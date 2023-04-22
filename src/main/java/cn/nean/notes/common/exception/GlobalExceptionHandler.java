package cn.nean.notes.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 捕获自定义 EmailException 异常
    @ExceptionHandler(EmailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //返回状态码 500
    @ResponseBody // 将信息返回为 json 格式
    public RestErrorResponse doTiktokException(EmailException e){
        log.error("捕获异常信息:{}",e.getErrMessage());
        return new RestErrorResponse(e.getErrMessage());
    }

    // 捕获自定义 EchoNotesException 异常
    @ExceptionHandler(EchoNotesException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //返回状态码 500
    @ResponseBody // 将信息返回为 json 格式
    public RestErrorResponse doTiktokException(EchoNotesException e){
        log.error("捕获异常信息:{}",e.getErrMessage());
        return new RestErrorResponse(e.getErrMessage());
    }

}
