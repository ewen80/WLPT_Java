package pw.ewen.WLPT.service;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domain.entity.MyResource;
import pw.ewen.WLPT.repository.MyResourceRepository;
import pw.ewen.WLPT.repository.specifications.MyResourceSpecificationBuilder;

import java.util.List;

/**
 * Created by wen on 17-2-24.
 */
@RestController
@RequestMapping(value = "/resources")
public class MyResourceController {
    private MyResourceRepository resourceRepository;

    public MyResourceController(MyResourceRepository resourceRepository){
        this.resourceRepository = resourceRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<MyResource> getResources(){
        return resourceRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<MyResource> getAll(@RequestParam(name = "filter", value = "") String filter){
        MyResourceSpecificationBuilder builder = new MyResourceSpecificationBuilder();
        return resourceRepository.findAll(builder.build(filter));
    }
}
