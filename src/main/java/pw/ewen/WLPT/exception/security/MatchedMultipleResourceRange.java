package pw.ewen.WLPT.exception.security;

/**
 * Created by wenliang on 17-2-28.
 * 多个匹配的ResourceRange记录（一个domain object只应该有一条对应自己userId的ResourceRange记录）
 */
public class MatchedMultipleResourceRange extends  RuntimeException {
    public MatchedMultipleResourceRange() {
        super("matched multiple resourcerange!");
    }

    public MatchedMultipleResourceRange(String message) {
        super(message);
    }
}
