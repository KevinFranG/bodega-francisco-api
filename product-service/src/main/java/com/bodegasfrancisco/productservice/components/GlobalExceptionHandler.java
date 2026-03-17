package com.bodegasfrancisco.productservice.components;

import com.bodegasfrancisco.components.CommonExceptionHandler;
import com.bodegasfrancisco.components.PsqlExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
    extends CommonExceptionHandler
    implements PsqlExceptionHandler {
}
