package pw.ewen.WLPT.security.acl;

import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;

/**
 * Created by wen on 17-2-26.
 * 根据domain object 找到acl_object_identity中的objectidentity策略实现
 * 由于acl数据库中实际保存的是object_range，所以需要从domain_object到object_range进行转换
 * 根据domain_object查找符合要求的object_range
 */
public class ObjectIdentityRetrievalStrategyWLPTImpl implements ObjectIdentityRetrievalStrategy {
    @Override
    public ObjectIdentity getObjectIdentity(Object domainObject) {
        //查找ResourceRepository中当前SID
        return null;
    }
}
