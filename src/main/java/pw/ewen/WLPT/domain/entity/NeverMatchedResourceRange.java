package pw.ewen.WLPT.domain.entity;

import pw.ewen.WLPT.domain.Resource;
import pw.ewen.WLPT.domain.ResourceRange;

/**
 * Created by wen on 17-3-7.
 * 一个特殊的，没有任何sid匹配到的资源范围
 * 用于在通过Resource寻找对应ResourceRange，没有对应条目后返回的一个替代ResouceRange
 */
public class NeverMatchedResourceRange extends ResourceRange {
    public NeverMatchedResourceRange() {
        super("","");
    }

    @Override
    public Class<?> repositoryClass() {
        return NeverMatchedResourceRange.class;
    }
}
