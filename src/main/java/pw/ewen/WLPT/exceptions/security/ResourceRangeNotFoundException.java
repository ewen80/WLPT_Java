package pw.ewen.WLPT.exceptions.security;

/**
 * Created by wen on 17-2-27.
 * 没有找到相应的资源范围
 */
public class ResourceRangeNotFoundException extends  RuntimeException {
    public ResourceRangeNotFoundException() {
        super("not found resourcerange ojbect!");
    }

    public ResourceRangeNotFoundException(String message){
        super(message);
    }
}
