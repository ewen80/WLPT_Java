package pw.ewen.WLPT.exception.security;

/**
 * Created by wen on 17-2-27.
 * 没有找到相应的资源范围
 */
public class NotFoundResourceRangeException extends  Exception {
    public NotFoundResourceRangeException(String message){
        super(message);
    }
}
