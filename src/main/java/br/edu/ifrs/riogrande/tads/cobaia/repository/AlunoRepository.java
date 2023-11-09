package br.edu.ifrs.riogrande.tads.cobaia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.edu.ifrs.riogrande.tads.cobaia.domain.entity.Aluno;

// <ClasseEntidade, TipoId>
public interface AlunoRepository extends CrudRepository<Aluno, Integer> {

  List<Aluno> findAll();

  @Query("SELECT a.nome FROM Aluno a WHERE a.cpf = :cpf") // JPQL
  String busca(@Param("cpf") String cpf);
  
  Optional<Aluno> findByCpf(String cpf);
}
