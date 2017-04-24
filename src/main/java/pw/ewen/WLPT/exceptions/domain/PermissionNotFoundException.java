package pw.ewen.WLPT.exceptions.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wenliang on 17-4-24.
 * 权限没有找找到
 */
@ResponseStatus( value = HttpStatus.NOT_FOUND, reason = "权限没有找到")
public class PermissionNotFoundException extends  RuntimeException {
}
