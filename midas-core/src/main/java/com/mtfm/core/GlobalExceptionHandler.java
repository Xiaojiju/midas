/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.core;

import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.core.context.response.RestResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 业务异常捕获
 * @author 一块小饼干
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public RestResult<Void> exception(Exception e) {
        e.printStackTrace();
        return RestResult.complete(ResultCode.ERROR);
    }

    @ExceptionHandler({ServiceException.class})
    public RestResult<Void> serviceException(ServiceException e) {
        return RestResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({BindException.class })
    public RestResult<Void> bindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return parseError(fieldErrors);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class })
    public RestResult<Void> paramsException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return parseError(fieldErrors);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class })
    public RestResult<Void> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpInputMessage httpInputMessage = e.getHttpInputMessage();
        HttpHeaders headers = httpInputMessage.getHeaders();
        List<String> contentTypes = headers.get("Content-Type");
        return RestResult.error("request body missing and request body must accord with Content-Type:" + contentTypes);
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class })
    public RestResult<Void> paramsMissingException(MissingServletRequestParameterException missingException) {
        return RestResult.error("parameter " + missingException.getParameterName() + " could not be null");
    }

    private RestResult<Void> parseError(List<FieldError> fieldErrors) {
        StringBuilder msg = new StringBuilder();
        for (FieldError error : fieldErrors) {
            msg.append(error.getDefaultMessage()).append(";");
        }
        return RestResult.error(msg.toString());
    }
}
