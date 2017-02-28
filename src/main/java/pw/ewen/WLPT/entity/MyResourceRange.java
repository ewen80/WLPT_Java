package pw.ewen.WLPT.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pw.ewen.WLPT.exception.security.NotFoundResourceRangeException;
import pw.ewen.WLPT.repository.MyResourceRangeRepository;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by wenliang on 17-2-27.
 * 测试资源类
 */
@Entity
public class MyResourceRange extends  ResourceRange {
    @Autowired
    private MyResourceRangeRepository myResourceRangeRepository;

    public MyResourceRange(long id, String filter, String userId) {
        super(id, filter, userId);
    }


}
