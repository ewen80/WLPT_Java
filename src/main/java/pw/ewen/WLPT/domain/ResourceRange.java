package pw.ewen.WLPT.domain;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pw.ewen.WLPT.exception.security.MatchedMultipleResourceRange;
import pw.ewen.WLPT.exception.security.NotFoundResourceRangeException;
import pw.ewen.WLPT.repository.ResourceRangeRepository;

import javax.persistence.*;
import java.util.List;

/**
 * Created by wen on 17-2-26.
 * 资源范围抽象类
 * 代表某个范围的资源集合,只支持到用户id
 */
@MappedSuperclass
public abstract class ResourceRange {
    private long id;
    private String filter;
    private String userid;

    public ResourceRange(){

    }
    public ResourceRange(long id, String filter, String userId) {
        this.id = id;
        this.filter = filter;
        this.userid = userId;
    }

    @Id
    @GeneratedValue
    public long getId(){ return this.id;}
    public void setId(long value){ this.id = value;}

    //资源范围筛选依据（Spel）
    public String getFilter() {
        return filter;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }

    //userId
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    //仓储对象
    //不要序列化
//    @Transient
//    public ResourceRangeRepository getResourceRangeRepository() {
//        return resourceRangeRepository;
//    }
//    public void setResourceRangeRepository(ResourceRangeRepository resourceRangeRepository) {
//        this.resourceRangeRepository = resourceRangeRepository;
//    }

    //获得仓储类型
    public  abstract Class<?> getRepositoryClass();

    //根据domain object 和 userid 筛选符合条件的唯一 resourceRange对象
    public ResourceRange getOne(Object domainObject, String userId, ResourceRangeRepository resourceRangeRepository){
        //从ResourceRange仓储中获取所有userid和对应Type的filter
        List<? extends ResourceRange> ranges = resourceRangeRepository.findByUserId();
        //遍历所有filter进行判断表达式是否为true
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(domainObject);
        Expression exp;
        int matchedRangesNumber = 0;
        ResourceRange matchedResourceRange = null;
        for(ResourceRange range : ranges){
            exp = parser.parseExpression(range.getFilter());
            Boolean result = exp.getValue(context, Boolean.class);
            if(result){
                matchedRangesNumber++;
                //只匹配第一条记录
                if(matchedResourceRange == null){
                    matchedResourceRange = range;
                }
            }
        }
        if(matchedRangesNumber > 1){
            //匹配到多条记录
            throw new MatchedMultipleResourceRange("matched multipile resourcrange object to domain object: " + domainObject.toString());
        }else if(matchedResourceRange == null) {
            //没有匹配到记录
            throw new NotFoundResourceRangeException("can not find MatchedResouceRange to object: " + domainObject.toString());
        }

        return matchedResourceRange;
    };
}
