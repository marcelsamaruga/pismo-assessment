package com.pismo.assessment.config;

import com.pismo.assessment.dto.ErrorHandlerDto;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.exception.InvalidOperationTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionServiceHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseBody
    public ErrorHandlerDto handlerAccountNotFound(HttpServletRequest req, AccountNotFoundException ex) {
        return ErrorHandlerDto.builder()
                .code(001)
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidOperationTypeException.class)
    @ResponseBody
    public ErrorHandlerDto handlerBadRequest(HttpServletRequest req, InvalidOperationTypeException ex) {
        return ErrorHandlerDto.builder()
                .code(003)
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorHandlerDto handlerInternalServerError(HttpServletRequest req, Exception ex) {
        return ErrorHandlerDto.builder()
                .code(004)
                .message(ex.getMessage())
                .build();
    }

}
