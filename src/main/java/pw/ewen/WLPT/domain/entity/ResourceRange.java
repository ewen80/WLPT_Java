package pw.ewen.WLPT.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pw.ewen.WLPT.domain.Resource;
import pw.ewen.WLPT.exception.security.MatchedMultipleResourceRangeException;
import pw.ewen.WLPT.repository.ResourceRangeRepository;

import javax.persistence.*;
import java.util.List;

/**
 * Created by wen on 17-2-26.
 * 资源范围类
 * 代表某个范围的资源集合
 * Role和ResourceType不能为空,MatachAll只是在基于Role和ResourceType的基础上匹配全部资源(即忽略filter字段)
 */
@Entity
public class ResourceRange {
    private long id;
    private String filter;
    private Role role;
    private ResourceType resourceType;
    private boolean matchAll = false;


    public ResourceRange(){   }
    public ResourceRange(String filter, Role role, ResourceType resourceType) {
        this.filter = filter;
        this.role = role;
        this.resourceType = resourceType;
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

    @ManyToOne
    @JoinColumn(name = "role_Id", nullable = false)
    @JsonManagedReference
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "resourceType_Id", nullable = false)
    public ResourceType getResourceType() {
        return resourceType;
    }
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    //本ResourceRange是否匹配所有Resource
    public boolean isMatchAll() {
        return matchAll;
    }
    public void setMatchAll(boolean matchAll) {
        this.matchAll = matchAll;
    }


    /**
     * 根据domain object 和 roleId 筛选符合条件的唯一 resourceRange对象
     * @param role 角色
     * @param resourceRangeRepository 资源仓储类
     * @return 符合filter筛选条件的ResourceRange,如果没有匹配项返回 null
     */
    @Transient
    public ResourceRange matchRangeByResourceAndRole(Resource domainObject, Role role, ResourceRangeRepository resourceRangeRepository){
        //从ResourceRange仓储中获取所有和当前角色以及指定资源对应的filter
        List ranges = resourceRangeRepository.findByRole_idAndResourceType_className(role.getId(), domainObject.getClass().getTypeName());
        //遍历所有filter进行判断表达式是否为true
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(domainObject);
        Expression exp;
        int matchedRangesNumber = 0;
        ResourceRange matchedResourceRange = null;
        for(Object range : ranges){
            if(range instanceof  ResourceRange){
                //范围是否是全匹配范围
                if(!((ResourceRange) range).isMatchAll()) {
                    exp = parser.parseExpression(((ResourceRange) range).getFilter());
                    Boolean result = exp.getValue(context, Boolean.class);
                    if (result) {
                        matchedRangesNumber++;
                        //只匹配第一条记录
                        if (matchedResourceRange == null) {
                            matchedResourceRange = (ResourceRange) range;
                        }
                    }
                }else{
                    //如果是全匹配范围，则直接返回该范围
                    return (ResourceRange)range;
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
}
