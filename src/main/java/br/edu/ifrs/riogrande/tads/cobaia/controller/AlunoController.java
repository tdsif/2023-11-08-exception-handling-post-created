package br.edu.ifrs.riogrande.tads.cobaia.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifrs.riogrande.tads.cobaia.domain.dto.AlunoDTO;
import br.edu.ifrs.riogrande.tads.cobaia.domain.dto.NovoAluno;
import br.edu.ifrs.riogrande.tads.cobaia.domain.entity.Aluno;
import br.edu.ifrs.riogrande.tads.cobaia.domain.service.AlunoService;
import br.edu.ifrs.riogrande.tads.cobaia.repository.AlunoRepository;

// é uma boa prática versionar a API, definir o contrato
@Validated
@RequestMapping("/api/v1/alunos")
@RestController
public class AlunoController {
  // Controller nunca tem regras de negócio,
  // nem acessam a persistência diretamente,
  // nunca expor as entidades do domínio
  // private AlunoRepository alunoRepository;

  private AlunoService alunoService;
  

  public AlunoController(AlunoService alunoService) {
    this.alunoService = alunoService;
  }
  // alunos
  // DTO, Data Transfer Object
  @GetMapping
  public ResponseEntity<List<AlunoDTO>> getAlunos(@RequestParam(required = false) String nome) {
    return ResponseEntity.ok(alunoService.findAll());
  }

  @PostMapping
  public ResponseEntity salvarAluno(@RequestBody @Valid NovoAluno aluno) {
    // validações
    // 201
    AlunoDTO dto = alunoService.save(aluno);

    // /api/v1/alunos/{id}
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{matricula}")
      .buildAndExpand(dto.getMatricula())
      .toUri();

    return ResponseEntity.created(location).build();
  }

  // status: 200, 201, 202, 204, 404, 400, 422, 
  // /api/vi/alunos?id=23&display=historicos
  // /api/v1/alunos?id=23 EVITAR, GERALMENTE COLEÇÃO DO RECURSO
  // /api/v1/alunos/23 UM RECURSO, ANINHÁVEL
  // /api/v1/alunos/23/historicos
  //          1ro nível   2do nível         3ro nível
  // /api/v1/alunos/23/historicos/23f2343a/disciplinas

  //@GetMapping("/api/v1/alunos/{matricula}")
  // @PathVariable(name = "matricula", required = true) 
  @GetMapping("/{matricula}")
  public ResponseEntity<AlunoDTO> getAluno(@PathVariable @NonNull Integer matricula) {
    //Objects.requireNonNull(matricula);

    return ResponseEntity.ok(alunoService.load(matricula));

    // Optional<AlunoDTO> resp = alunoService.find(matricula);
    // if (resp.isPresent()) {
    //   return ResponseEntity.ok(resp.get()); // 200 OK {}
    // }
    
    // return ResponseEntity.notFound().build(); // 404 NOT FOUND    
  }

  @GetMapping("/erro")
  public ResponseEntity<?> erro() {
    throw new RuntimeException();    
  }
}
