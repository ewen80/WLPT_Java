package pw.ewen.WLPT.exceptions.security;

/**
 * Created by wenliang on 17-2-28.
 * 多个匹配的ResourceRange记录（一个domain object只应该有一条对应自己userId的ResourceRange记录）
 */
public class MatchedMultipleResourceRangesException extends  RuntimeException {
    public MatchedMultipleResourceRangesException() {
        super("matched multiple resourcerange!");
    }

    public MatchedMultipleResourceRangesException(String message) {
        super(message);
    }
}
