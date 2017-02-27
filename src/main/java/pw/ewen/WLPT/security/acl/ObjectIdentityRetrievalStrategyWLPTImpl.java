package pw.ewen.WLPT.security.acl;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import pw.ewen.WLPT.entity.HasRangeObject;
import pw.ewen.WLPT.entity.ResourceRange;

/**
 * Created by wen on 17-2-26.
 * 根据domain object 找到acl_object_identity中的objectidentity策略实现
 * 由于acl数据库中实际保存的是object_range，所以需要从domain_object到object_range进行转换
 * 根据domain_object查找符合要求的object_range
 */
public class ObjectIdentityRetrievalStrategyWLPTImpl implements ObjectIdentityRetrievalStrategy {
    @Override
    public ObjectIdentity getObjectIdentity(Object domainObject) {
        //查找ResourceRepository中当前SID对应的ResourceRange

        return null;
    }

    //从domain object获得ResourceRange范围对象
    private ResourceRange getResourceRange(Object domainObject) throws IllegalAccessException, InstantiationException {
        //根据domainObject获得对应的object_range类
        //  只处理实现了HasRangeObject接口的类
        if(domainObject instanceof HasRangeObject){
            Class rangeClass = ((HasRangeObject) domainObject).getRangeObjectClass();
            ResourceRange range = (ResourceRange)rangeClass.newInstance();
        }
        EvaluationContext context = new StandardEvaluationContext(domainObject);
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(condition);
        String result = exp.getValue(context, Boolean.class).toString();
    }
}
