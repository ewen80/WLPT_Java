package pw.ewen.WLPT.repository.specifications.core;

/**
 * Created by wenliang on 17-3-29.
 * 查询操作符
 */
public enum SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, GREATER_THAN_EQUALITY, LESS_THAN, LESS_THAN_EQUALITY, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS;

    public static final String[] SIMPLE_OPERATION_SET = { ":", "!", ">", ">:", "<", "<:", "~" };

    public static SearchOperation getSimpleOperation(String input) {
        switch (input) {
            case ":":   //等于
                return EQUALITY;
            case "!":   //不等于
                return NEGATION;
            case ">":   //大于
                return GREATER_THAN;
            case ">:":   //大于等于
                return GREATER_THAN_EQUALITY;
            case "<":   //小于
                return LESS_THAN;
            case "<:":  //小于等于
                return LESS_THAN_EQUALITY;
            case "~":   //Like
                return LIKE;
            default:
                return null;
        }
    }
}
