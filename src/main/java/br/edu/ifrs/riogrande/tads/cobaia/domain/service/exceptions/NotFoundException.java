package br.edu.ifrs.riogrande.tads.cobaia.domain.service.exceptions;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("Recurso Não Encontrado");
  }

  public NotFoundException(String msg) {
    super(msg);
  }
}
