package pw.ewen.WLPT.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domain.entity.ResourceList;
import pw.ewen.WLPT.repository.ResourceListRepository;

import java.util.List;

/**
 * Created by wen on 17-3-12.
 */
@RestController
@RequestMapping(value = "/resourcelist")
public class ResourceListController {
    private ResourceListRepository resourceListRepository;

    /**
     * 获取所有资源列表
     * @return
     */
    public List<ResourceList> getAllResourceList(){
        return resourceListRepository.findAll();
    }
}
