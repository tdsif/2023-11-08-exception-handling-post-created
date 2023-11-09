package br.edu.ifrs.riogrande.tads.cobaia.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.edu.ifrs.riogrande.tads.cobaia.domain.dto.AlunoDTO;
import br.edu.ifrs.riogrande.tads.cobaia.domain.dto.NovoAluno;
import br.edu.ifrs.riogrande.tads.cobaia.domain.entity.Aluno;
import br.edu.ifrs.riogrande.tads.cobaia.domain.service.exceptions.NotFoundException;
import br.edu.ifrs.riogrande.tads.cobaia.domain.service.exceptions.ServiceException;
import br.edu.ifrs.riogrande.tads.cobaia.repository.AlunoRepository;

@Service // Bean
public class AlunoService {

  private final AlunoRepository alunoRepository;

  public AlunoService(AlunoRepository alunoRepository) {
    this.alunoRepository = alunoRepository;
  }

  private AlunoDTO toDTO(Aluno aluno) {
    AlunoDTO dto = new AlunoDTO();
    BeanUtils.copyProperties(aluno, dto);
    // dto.setMatricula(aluno.getMatricula());
    // dto.setNome(aluno.getNome());
    return dto;
  }
  
  public List<AlunoDTO> findAll() {
    // one-liner
    return alunoRepository.findAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());

    // List<AlunoDTO> dtos = new ArrayList<>();
    // for (Aluno a : alunos) {
    //   AlunoDTO dto = new AlunoDTO();
    //   dto.setMatricula(a.getMatricula());
    //   dto.setNome(a.getNome());
    //   dtos.add(dto);
    // }
    // return dtos;
  }

  public Optional<AlunoDTO> find(@NonNull Integer matricula) {
    return alunoRepository
        .findById(matricula)
        .map(this::toDTO);
  }

  // service deve ser agnóstico quanto à plataforma que está (ex: web, desktop, ...)
  // ex.: não se retorna ResponseEntity aqui vvv
  //public ResponseEntity<AlunoDTO> load(@NonNull Integer matricula) {

  public AlunoDTO load(@NonNull Integer matricula) {
    return find(matricula)
        .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));
  }

  public AlunoDTO save(NovoAluno  novoAluno) {

    if (alunoRepository.findByCpf(novoAluno.getCpf()).isPresent()) {
      throw new ServiceException("Já existe um aluno com o CPF " + novoAluno.getCpf());
    }

    Aluno aluno = new Aluno();
    BeanUtils.copyProperties(novoAluno, aluno);

    return toDTO(alunoRepository.save(aluno));
  }

  // public @NonNull String fazAlgo(@NonNull String s) {
  //   String s2 = s.toLowerCase();
  //   return s2;
  // }
}
