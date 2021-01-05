package com.yellowzero.backend;

import com.yellowzero.backend.model.JsonResult;
import com.yellowzero.backend.model.Status;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler {

    /**
     *  拦截Exception类的异常
     * @param e
     * @return JsonResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult exceptionHandler(Exception e){
        e.printStackTrace();
        if (e instanceof MissingServletRequestParameterException)
            return new JsonResult(Status.BAD_REQUEST);
        return new JsonResult(Status.INTERNAL_INTERNAL_SERVER_ERROR);
    }
}
