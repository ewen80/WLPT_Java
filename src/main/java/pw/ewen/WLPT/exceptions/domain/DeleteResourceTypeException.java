package pw.ewen.WLPT.exceptions.domain;

/**
 * created by wenliang on 2021/3/27
 * ResourceType删除错误
 */
public class DeleteResourceTypeException extends RuntimeException{
    private static final long serialVersionUID = 522181440554070001L;

    public DeleteResourceTypeException(String message) {
        super(message);
    }
}
