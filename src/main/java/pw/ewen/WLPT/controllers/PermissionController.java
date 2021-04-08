package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.ResourceRangePermissionWrapperDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.exceptions.domain.PermissionNotFoundException;
import pw.ewen.WLPT.services.PermissionService;
import pw.ewen.WLPT.services.ResourceRangeService;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by wen on 17-3-5.
 * 权限控制Controller
 */
@RestController
@RequestMapping(value = "/permissions")
public class PermissionController {

    private final PermissionService permissionService;
    private final ResourceTypeService resourceTypeService;
    private final ResourceRangeService resourceRangeService;
    private final RoleService roleService;

    public static final Permission[] SUPPORT_PERMISSIONS = { BasePermission.READ, BasePermission.WRITE };

    @Autowired
    public PermissionController(PermissionService permissionService,
                                ResourceTypeService resourceTypeService,
                                ResourceRangeService resourceRangeService,
                                RoleService roleService) {
        this.permissionService = permissionService;
        this.resourceTypeService = resourceTypeService;
        this.resourceRangeService = resourceRangeService;
        this.roleService = roleService;
    }

//    /**
//     * 根据ResourceType得到对应ResourceRange权限
//     */
//    @RequestMapping(value = "/ResourceTypeAndRole", method = RequestMethod.GET, produces = "application/json")
//    public Set<ResourceRangePermissionWrapperDTO> getByResourceTypeAndRole(@RequestParam(value = "className",defaultValue = "") String resourceTypeClassName, @RequestParam(value = "roleId", defaultValue = "") String roleId) {
//        List<ResourceRange> ranges = this.resourceRangeService.findByResourceType(resourceTypeClassName);
//        if(ranges.size() > 0) {
//            StringBuilder sb = new StringBuilder();
//            ranges.forEach( (range) -> { sb.append(range.getId()).append(',');});
//            return this.getByResourceRanges(sb.toString());
//        }
//        return new HashSet<>();
//    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Set<ResourceRangePermissionWrapperDTO> getPermissionWrappers(String filter) {
        List<ResourceRange> ranges = this.resourceRangeService.findAll(filter);
        if(ranges.size() > 0) {
            StringBuilder sb = new StringBuilder();
            ranges.forEach( (range) -> { sb.append(range.getId()).append(',');});
            return this.getByResourceRanges(sb.toString());
        }
        return new HashSet<>();
    }

    /**
     * 获取一个或者多个ResourceRange权限
     * 多个ResourceRange用,分割
     */
    @RequestMapping(value = "/ResourceRange", method = RequestMethod.GET, produces = "application/json")
    public Set<ResourceRangePermissionWrapperDTO> getByResourceRanges(@RequestParam("resourceRangeIds") String resourceRangeIds) throws IllegalArgumentException{
        Set<ResourceRangePermissionWrapperDTO> wrappers = new HashSet<>();

        String[] arrResourceRangeIds = resourceRangeIds.split(",");

        for(String id : arrResourceRangeIds){
            try{
                long resourceRangeId = Long.parseLong(id);
                ResourceRangePermissionWrapper wrapper = this.permissionService.getByResourceRange(resourceRangeId);
                if(wrapper != null) {
                    ResourceRangePermissionWrapperDTO dto = ResourceRangePermissionWrapperDTO.convertFromPermissionWrapper(wrapper);
                    wrappers.add(dto);
                }
            }catch(NumberFormatException e){
                throw new IllegalArgumentException("ResourceRangeId必须是数字");
            }
        }
        return wrappers;
    }

    /**
     * 保存权限
     * @return int  插入的权限记录数
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @Transactional
    public int save(@RequestBody ResourceRangePermissionWrapperDTO wrapperDTO) {
        // 检查ResourceRange是否已经保存，如果没保存先保存
        ResourceRangeDTO rangeDTO = wrapperDTO.getResourceRangeDTO();
        ResourceRange range = rangeDTO.convertToResourceRange(this.roleService, this.resourceTypeService);
        if(rangeDTO.getId() == 0) {
            // 保存ResourceRange
            this.resourceRangeService.save(range);
        }

        this.permissionService.deleteResourceRangeAllPermissions(wrapperDTO.getResourceRangeDTO().getId());

        int insertNumber = 0;

        for (PermissionDTO pDTO : wrapperDTO.getPermissions()) {
            Optional<Permission> permission = Arrays.stream(SUPPORT_PERMISSIONS)
                    .filter(pm -> pm.getMask() == pDTO.getMask())
                    .findFirst();
            if(permission.isPresent()) {
                try {
                    this.permissionService.insertPermission(range.getId(), permission.get());
                    insertNumber++;
                } catch (Exception ignored) {

                }
            }
        }
        return insertNumber;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{resourceRangeIds}")
    @Transactional
    public void delete(@PathVariable(value = "resourceRangeIds") String resourceRangeIds) {
        String[] arrResourceRangeIds = resourceRangeIds.split(",");
        // 删除所有权限
        for(String id : arrResourceRangeIds) {
            this.permissionService.deleteResourceRangeAllPermissions(Long.parseLong(id));
        }
        // 删除资源范围
        this.resourceRangeService.delete(arrResourceRangeIds);
    }

//    /**
//     * 系统权限初始化，使用admin用户执行该API
//     * @return
//     */
//    @Transactional
//    @RequestMapping(method = RequestMethod.GET, value = "init", produces = "application/json")
//    public void init() {
//        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.Menu", "menu", "菜单");
//        this.resourceTypeService.save((menuResourceType));
//
//        Role adminRole = this.roleService.findOne("admin");
//
//        ResourceRange haveAllMenuPermission = new ResourceRange("", adminRole, menuResourceType);
//        haveAllMenuPermission.setMatchAll();
//        this.resourceRangeService.save(haveAllMenuPermission);
//
//        this.permissionService.insertPermissions(haveAllMenuPermission.getId(), Arrays.asList(SUPPORT_PERMISSIONS));
//    }
}
