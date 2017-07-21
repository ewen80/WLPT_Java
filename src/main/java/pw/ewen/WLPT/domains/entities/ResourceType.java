package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import pw.ewen.WLPT.domains.Resource;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wen on 17-3-12.
 * 保存当前系统中的资源类别列表
 */
@Entity
public class ResourceType extends Resource implements Serializable {

    private String className;
    private String name;
    private String description;
    private boolean deleted = false;
    @JsonManagedReference(value = "type")
    private Set<ResourceRange> resourceRanges;
//    private Set<MyResource> resources;

    protected ResourceType(){}

    public ResourceType(String className, String name, String description, boolean deleted) {
        this.className = className;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
        this.resourceRanges = new HashSet<>();
//        this.resources = new HashSet<>();
    }

    public ResourceType(String className){
        this(className,className);
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
    @Column(unique = true)
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
    @OneToMany(mappedBy = "resourceType")
    public Set<ResourceRange> getResourceRanges() { return this.resourceRanges;}
    public void setResourceRanges(Set<ResourceRange> resourceRanges) { this.resourceRanges = resourceRanges;}

    /**
     * 根据Resource返回对应的ResourceType
     * @param resource
     * @param repository
     * @return
     */
    @Autowired
    public static ResourceType getFromResource(Resource resource, ResourceTypeRepository repository){
        String resourceTypeName = resource.getClass().getTypeName();
        return repository.findByClassName(resourceTypeName);
    }

//    /**
//     * 该类型下的具体资源
//     */
//    @OneToMany(mappedBy = "resourceType")
//    public Set<MyResource> getResources() {
//        return resources;
//    }
//    public void setResources(Set<MyResource> resources) {
//        this.resources = resources;
//    }
}
