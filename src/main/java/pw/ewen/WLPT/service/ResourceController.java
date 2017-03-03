package pw.ewen.WLPT.service;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domain.entity.MyResource;
import pw.ewen.WLPT.repository.MyResourceRepository;

import java.util.List;

/**
 * Created by wen on 17-2-24.
 */
@RestController
@RequestMapping(value = "/resources")
//@PostFilter("hasPermission(filterObject, 'READ')")
public class ResourceController {
    private MyResourceRepository resourceRepository;

    public ResourceController(MyResourceRepository resourceRepository){
        this.resourceRepository = resourceRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PostFilter("hasPermission(filterObject, 'read')")
    public List<MyResource> getAllResources(){
        return resourceRepository.findAll();
    }

    @RequestMapping(value = "/spel/{resourceId}", method = RequestMethod.GET)
    public String testSpel(@PathVariable("resourceId") long resourceId){
        MyResource resource = resourceRepository.findOne(resourceId);

        String condition = "number > 50";

        EvaluationContext context = new StandardEvaluationContext(resource);
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(condition);
        String result = exp.getValue(context, Boolean.class).toString();
        return result;
    }
}
