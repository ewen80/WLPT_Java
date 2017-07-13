package pw.ewen.WLPT.exceptions.enviroment;

/**
 * Created by wenliang on 17-7-13.
 * 配置文件中的属性找不到
 */
public class PropertyNotFound extends RuntimeException {
    public PropertyNotFound(String propertyName) {
        super(propertyName + "类的权限定名在配置文件中找不到");
    }

}
