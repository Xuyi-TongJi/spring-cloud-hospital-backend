package edu.seu.yygh.common.exception;

import edu.seu.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xuyitjuseu
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Object> error(Exception e) {
        Result<Object> result = Result.fail();
        result.message(e.getMessage());
        return result;
    }

    @ExceptionHandler(YyghException.class)
    public Result<Object> yyghError(YyghException e) {
        Result<Object> result = Result.fail();
        result.setCode(e.getCode());
        result.message(e.getMessage());
        return result;
    }
}
