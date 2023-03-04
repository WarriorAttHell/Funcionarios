package br.edu.unoesc.funcionarios.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.funcionarios.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	public List<Funcionario> findByNomeContainingIgnoreCase(String nome);
	
	public Page<Funcionario> findByNomeContainingIgnoreCase(String nome, Pageable pagina);
	
	@Query("Select f from Funcionario f where f.salario >= :min and f.salario <= :max")
	public List<Funcionario> porFaixaSalarial(@Param("min") BigDecimal min,
											  @Param("max") BigDecimal max);
	
	@Query("Select f from Funcionario f where f.numDep > 0")
	public List<Funcionario> porPossuiDependentes();
}