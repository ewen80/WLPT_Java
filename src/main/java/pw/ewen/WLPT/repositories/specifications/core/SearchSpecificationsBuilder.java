package pw.ewen.WLPT.repositories.specifications.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.split;

/**
 * Created by wenliang on 17-3-30.
 */
public class SearchSpecificationsBuilder<T> {
    private final List<SearchCriteria> params = new ArrayList<>();

    /**
     * 重置查询条件
     */
    public void reset() {
        params.clear();
    }

    //生成builder原材料
    private SearchSpecificationsBuilder<T> with(
            String key, String operation, Object value, String prefix, String suffix) {

        SearchOperation op = SearchOperation.getSimpleOperation(operation);
        if (op != null) {
            //如果操作符为:,但是value中有*,判断操作类型是 CONTAINS,ENDS_WITH,STARTS_WITH中的一种
            if (op == SearchOperation.EQUALITY) {
                boolean startWithAsterisk = prefix.contains("*");
                boolean endWithAsterisk = suffix.contains("*");

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            } else if (op == SearchOperation.IN) {
                //如果是包含操作，将value中下划线分割的字符串转换成HashSet
                String[] arrValue = StringUtils.split(value.toString(), '_');
                value = new HashSet<>(Arrays.asList(arrValue));
            }
            params.add(new SearchCriteria(key, op, value));
        }
        return this;
    }

    //对过滤字符串分析，并进行build生成Specification<>
    public Specification<T> build(String filterString) {

        String operationSetExper = StringUtils.join(SearchOperation.SIMPLE_OPERATION_SET, '|');
        Pattern pattern = Pattern.compile(
                "(\\S+?)(" + operationSetExper + ")(\\*?)(\\S+?|[\\u4E00-\\u9FA5]+?)(\\*?),");
        Matcher matcher = pattern.matcher(filterString + ",");
        while (matcher.find()) {
            this.with(
                    matcher.group(1), //字段
                    matcher.group(2),	//操作
                    matcher.group(4),	//值
                    matcher.group(3),	//前置操作符
                    matcher.group(5));	//后置操作符
        }

        if (params.size() == 0) {
            return null;
        }

        List<Specification<T>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new SearchSpecification<>(param));
        }

        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}
