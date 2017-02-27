package pw.ewen.WLPT.security.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.acls.domain.IdentityUnavailableException;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import pw.ewen.WLPT.entity.HasRangeObject;
import pw.ewen.WLPT.entity.ResourceRange;
import pw.ewen.WLPT.exception.security.NotFoundResourceRangeException;
import pw.ewen.WLPT.security.UserContext;

/**
 * Created by wen on 17-2-26.
 * 根据domain object 找到acl_object_identity中的objectidentity策略实现
 * 由于acl数据库中实际保存的是object_range，所以需要从domain_object到object_range进行转换
 * 根据domain_object查找符合要求的object_range
 */
public class ObjectIdentityRetrievalStrategyWLPTImpl implements ObjectIdentityRetrievalStrategy {
    @Autowired
    private UserContext userContext;

    @Override
    public ObjectIdentity getObjectIdentity(Object domainObject) throws IdentityUnavailableException {
        //查找ResourceRepository中当前SID对应的ResourceRange
        ResourceRange resourceRange = getResourceRange(domainObject);
        if(resourceRange == null){
            throw new IdentityUnavailableException("ResourceRange can not be null");
        }else{
            return new ObjectIdentityImpl(resourceRange);
        }

    }

    //从domain object获得ResourceRange范围对象
    private ResourceRange getResourceRange(Object domainObject)  {
        //根据domainObject获得对应的object_range类
        String rangeFilter;
        //  只处理实现了HasRangeObject接口的类
        if(domainObject instanceof HasRangeObject){
            Class rangeClass = ((HasRangeObject) domainObject).getRangeObjectClass();
            try {
                return ((ResourceRange)rangeClass.newInstance()).getOne(domainObject, userContext.getCurrentUser().getId());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return null;

    }
}
