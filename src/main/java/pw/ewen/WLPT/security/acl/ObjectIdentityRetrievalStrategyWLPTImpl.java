package pw.ewen.WLPT.security.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.acls.domain.IdentityUnavailableException;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityGenerator;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import pw.ewen.WLPT.domain.HasResourceRangeObject;
import pw.ewen.WLPT.domain.ResourceRange;
import pw.ewen.WLPT.repository.ResourceRangeRepository;
import pw.ewen.WLPT.security.UserContext;

import java.io.Serializable;

/**
 * Created by wen on 17-2-26.
 * 根据domain object 找到acl_object_identity中的objectidentity策略实现
 * 由于acl数据库中实际保存的是object_range，所以需要从domain_object到object_range进行转换
 * 根据domain_object查找符合要求的object_range
 */
@Component
public class ObjectIdentityRetrievalStrategyWLPTImpl implements ObjectIdentityRetrievalStrategy {
    @Autowired
    private UserContext userContext;
    @Autowired
    private ApplicationContext appContext;

    @Override
    public ObjectIdentity getObjectIdentity(Object domainObject) {
        //查找ResourceRepository中当前SID对应的ResourceRange
        Assert.isInstanceOf(HasResourceRangeObject.class, domainObject);

        String currentUserRoleId = userContext.getCurrentUser().getRole().getID();
        ResourceRange resourceRange = getResourceRange((HasResourceRangeObject) domainObject, currentUserRoleId);
        if(resourceRange == null){
            throw new IdentityUnavailableException("can not find mateched ResourceRange Object");
        }else{
            return new ObjectIdentityImpl(resourceRange);
        }
//        return null;
    }

    /**
     * 从domain object获得ResourceRange范围对象
     * @Return 匹配的ResourceRange，如果没有匹配对象则返回一个固定ResourceRange(任何用户不能对此ResourceRange有权限)
     */
    private ResourceRange getResourceRange(HasResourceRangeObject domainObject, String roleId)  {
//        Assert.notNull(roleId);

        //根据domainObject获得对应的object_range类
        Class resourceRangeClass = domainObject.getResourceRangeObjectClass();
        try {
            ResourceRange range = ((ResourceRange)resourceRangeClass.newInstance());
            Class<?> repositoryClass = range.repositoryClass();
            //获取具体ResourceRange子类的仓储Bean
            ResourceRangeRepository resourceRangeRepository = (ResourceRangeRepository)appContext.getBean(repositoryClass);
            ResourceRange matchedRange = range.selectOne(domainObject, roleId, resourceRangeRepository);
            //如果没有匹配到返回一个No_User_Matched_ResourceRange
            matchedRange = matchedRange == null ? range.generate_No_Role_Matched_ResourceRange() : matchedRange;
            return  matchedRange;

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
