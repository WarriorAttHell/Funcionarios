package br.edu.unoesc.funcionarios.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.unoesc.funcionarios.dto.FuncionarioDTO;
import br.edu.unoesc.funcionarios.model.Funcionario;

public interface FuncionarioService {
	void popularTabelaInicial();

	Funcionario incluir(Funcionario funcionario);
	Funcionario alterar(Long id, Funcionario funcionario);
	void excluir(Long id);

	List<Funcionario> listar();
	Page<FuncionarioDTO> listarPaginado(Pageable pagina);
	
	Funcionario buscar(Long id);	  // Lança uma exceção caso o não exista o funcionario com id procurado
	Funcionario buscarPorId(Long id); // Retorna um novo objeto Livro caso id não seja encontrado
	Optional<Funcionario> porId(Long id);
	
	List<Funcionario> buscarPorNome(String nome);
	List<Funcionario> buscarPorFaixaSalarial(BigDecimal salarioMinimo, BigDecimal salarioMaximo);
	List<Funcionario> buscarPossuiDependentes();
}