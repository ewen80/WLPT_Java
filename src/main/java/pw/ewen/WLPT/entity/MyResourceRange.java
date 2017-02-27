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
 */
@Entity
public class MyResourceRange extends  ResourceRange {
    @Autowired
    private MyResourceRangeRepository myResourceRangeRepository;

    public MyResourceRange(long id, String filter, String userId) {
        super(id, filter, userId);
    }

    @Override
    public ResourceRange getOne(Object domainObject, String userId) throws  NotFoundResourceRangeException {
        //从ResourceRange仓储中获取所有userid和对应Type的filter
        List<MyResourceRange> ranges =  myResourceRangeRepository.findByUserId();
        //遍历所有filter进行判断表达式是否为true
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(domainObject);
        Expression exp;
        for(MyResourceRange range : ranges){
            exp = parser.parseExpression(range.getFilter());
            Boolean result = exp.getValue(context, Boolean.class);
            if(result){
                return range;
            }
        }
        throw  new NotFoundResourceRangeException("can not find MatchedResouceRange to object: " + domainObject.toString());

    }
}
