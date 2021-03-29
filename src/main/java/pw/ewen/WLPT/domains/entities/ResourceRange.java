package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pw.ewen.WLPT.domains.Resource;
import pw.ewen.WLPT.exceptions.security.MatchedMultipleResourceRangesException;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by wen on 17-2-26.
 * 资源范围类
 * 代表某个范围的资源集合
 * Role和ResourceType不能为空,如果filter为空，表示角色对该对象有全部权限
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"role_Id", "resourceType_Id"}))
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class ResourceRange {

    private long id;
    private String filter;
//    @JsonBackReference(value = "range")
    private Role role;
//    @JsonBackReference(value = "type")
    private ResourceType resourceType;

//    private boolean matchAll = false;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public ResourceRange(){   }
    public ResourceRange(String filter, Role role, ResourceType resourceType) {
        this.filter = filter;
        this.role = role;
        this.resourceType = resourceType;
    }

    //资源范围筛选依据（Spel）
    @Column(nullable = false)
    public String getFilter() {
        return filter;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }

    @ManyToOne
    @JoinColumn(name = "role_Id", nullable = false)
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

    /**
     * 本ResourceRange是否匹配所有Resource
     * @return
     */
    @Transient
    public boolean isMatchAll() {
        return this.filter.isEmpty();
    }

    public void setMatchAll() {
        this.filter = "";
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
        List<ResourceRange> ranges = resourceRangeRepository.findByRole_idAndResourceType_className(role.getId(), domainObject.getClass().getTypeName());
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
            throw new MatchedMultipleResourceRangesException("matched multipile resource ranges object to domains object: " + domainObject.toString());
        } //没有匹配到记录，matchedResourceRange=null，函数返回null

        return matchedResourceRange;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        // 如果role,filter和type都一样则认为两个range相等
        ResourceRange rangeObj = (ResourceRange)obj;
        return this.role.equals(rangeObj.getRole()) && this.filter.equals(rangeObj.getFilter()) && this.resourceType.equals(rangeObj.resourceType);
    }
}
