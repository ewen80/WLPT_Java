package pw.ewen.WLPT.controllers;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.Resource;

/**
 * Created by wen on 17-3-5.
 * 权限控制Controller
 * TODO:移植逻辑到services层
 */
@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

    public void savePermission(Resource resource, Sid sid, Permission permission){

    }
}
