package org.tddpetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.tddpetclinic.dto.ErrorDto;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> catchValidationException(MethodArgumentNotValidException exception,
                                                             WebRequest webRequest){
        ErrorDto errorDto = new ErrorDto();
        if (exception.getFieldError() != null
                && StringUtils.hasText(exception.getFieldError().getDefaultMessage())) {
            errorDto.setMessage(exception.getFieldError().getDefaultMessage());
        }
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
