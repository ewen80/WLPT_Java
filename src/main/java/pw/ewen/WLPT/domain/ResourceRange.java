package pw.ewen.WLPT.domain;

import org.springframework.data.repository.query.Param;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pw.ewen.WLPT.exception.security.MatchedMultipleResourceRangeException;
import pw.ewen.WLPT.exception.security.NotFoundResourceRangeException;
import pw.ewen.WLPT.repository.ResourceRangeRepository;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
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
    private String roleId;

    protected ResourceRange(){   }
    protected ResourceRange(long id, String filter, String roleId) {
        this.id = id;
        this.filter = filter;
        this.roleId = roleId;
    }

    @Id
//    @GeneratedValue
    public long getId(){ return this.id;}
    public void setId(long value){ this.id = value;}

    //资源范围筛选依据（Spel）
    public String getFilter() {
        return filter;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }

    //roleId
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    //获得仓储类型
    @Transient
    public abstract  Class<?> repositoryClass();

    /**
     * 根据domain object 和 roleId 筛选符合条件的唯一 resourceRange对象
     * @param domainObject
     * @param 角色Id
     * @param 资源仓储类
     * @return 符合filter筛选条件的ResourceRange,如果没有匹配项返回 null
     */
    @Transient
    public ResourceRange selectOne(Object domainObject, String roleId, ResourceRangeRepository resourceRangeRepository){
        //从ResourceRange仓储中获取所有roleId和对应Type的filter
        List<? extends  ResourceRange> ranges = resourceRangeRepository.findByRoleId(roleId);
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
            throw new MatchedMultipleResourceRangeException("matched multipile resourcrange object to domain object: " + domainObject.toString());
        }else if(matchedResourceRange == null) {
            //没有匹配到记录
            return null;
        }

        return matchedResourceRange;
    }

    public abstract ResourceRange generate_No_Role_Matched_ResourceRange();
}
