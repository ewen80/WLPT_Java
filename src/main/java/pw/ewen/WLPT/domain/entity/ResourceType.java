package pw.ewen.WLPT.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by wen on 17-3-12.
 * 保存当前系统中的资源类别列表
 */
@Entity
public class ResourceType {

    private String className;
    private String name;
    private String description;
    private boolean deleted = false;

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
}
