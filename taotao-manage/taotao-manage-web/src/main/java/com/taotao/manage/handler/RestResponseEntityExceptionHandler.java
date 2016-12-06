package com.taotao.manage.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.taotao.common.bean.Failure;
import com.taotao.common.bean.Message;
import com.taotao.common.exception.CodedException;
import com.taotao.common.exception.ErrorCode;
import com.taotao.common.track.Track;


/**
 * 控制层异常封装处理<br/>
 * <ol>
 * <li>当CodedException时，则返回错误编码和消息；</li>
 * <li>非CodedException时，则包装为500，系统异常，保证前端页面正常；</li>
 * </ol>
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = { Throwable.class })
    @ResponseBody
    protected ResponseEntity<Message> handleConflict(Throwable throwable,WebRequest request) {
        Message response = null;
        if (throwable instanceof CodedException) {
            CodedException ex = (CodedException) throwable;
            final ErrorCode code = ex.getErrorCode();
            response = new Failure(code.getErrorCode(),code.getMessage());
            Track.response(response.toString());
            return new ResponseEntity<Message>(response, HttpStatus.BAD_REQUEST);
        } else {
            response = new Failure(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),"系统异常");
            Track.response(response.toString());
            return new ResponseEntity<Message>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
