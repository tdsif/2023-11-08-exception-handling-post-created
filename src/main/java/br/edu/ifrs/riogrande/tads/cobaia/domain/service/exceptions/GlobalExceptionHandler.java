package br.edu.ifrs.riogrande.tads.cobaia.domain.service.exceptions;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  //private final static Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

  @ExceptionHandler(NotFoundException.class)
  ResponseEntity<?> handleNotFoundException(NotFoundException e) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Map.of("erro", e.getMessage())); // {"erro": "Aluno não encontrado"}
  }

  @ExceptionHandler(ServiceException.class)
  ResponseEntity<?> handleServiceException(ServiceException e) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(Map.of("erro", e.getMessage())); // {"erro": "Aluno não encontrado"}
  }
  
  @ExceptionHandler(Exception.class) // catch all
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
  void handleException(Exception e) {
    log.error("Generic Exception", e);
  }
}
