package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import pw.ewen.WLPT.domains.Resource;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by wen on 17-3-12.
 * 保存当前系统中的资源类别列表
 */
@Entity
public class ResourceType implements Serializable {

    private static final long serialVersionUID = -2617247962702444217L;

    private String className;
    private String name;
    private String description;
    private boolean deleted = false;
//    @JsonManagedReference(value = "type")
    private Set<ResourceRange> resourceRanges = new HashSet<>();

    protected ResourceType(){}

    public ResourceType(String className, String name, String description, boolean deleted) {
        this.className = className;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
    }

    public ResourceType(String className, String name){
        this(className,name,"",false);
    }

    public ResourceType(String className,String name, String description){
        this(className,name,description,false);
    }

    /**
     * 类的全名称
     */
    @Id
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 资源名称
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 资源描述
     */
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 软删除标记
     */
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 资源范围
     */
    @OneToMany(mappedBy = "resourceType", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Set<ResourceRange> getResourceRanges() { return this.resourceRanges;}
    public void setResourceRanges(Set<ResourceRange> resourceRanges) { this.resourceRanges = resourceRanges;}

    /**
     * 根据Resource返回对应的ResourceType
     */
    @Autowired
    public static ResourceType getFromResouce(Resource resource, ResourceTypeRepository repository){
        String resourceTypeName = resource.getClass().getTypeName();
        return repository.getOne(resourceTypeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ResourceType type = (ResourceType)obj;

        return Objects.equals(className, type.getClassName());
    }
}
