package br.edu.unoesc.funcionarios.service_impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unoesc.funcionarios.dto.FuncionarioDTO;
import br.edu.unoesc.funcionarios.model.Funcionario;
import br.edu.unoesc.funcionarios.repository.FuncionarioRepository;
import br.edu.unoesc.funcionarios.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	@Autowired
	private FuncionarioRepository repositorio;
	
	@Override
	public void popularTabelaInicial() {
		repositorio.saveAll(List.of(
				new Funcionario(null, "Fulano", 0, new BigDecimal("123.45"), LocalDate.of(2000, 10, 5)),
				new Funcionario(null, "Beltrano", 2, new BigDecimal("42.42"), LocalDate.of(2001, 12, 28)),
				new Funcionario(null, "Sicrano", 3, new BigDecimal("666"), LocalDate.of(1975, 6, 6)),
				new Funcionario(null, "Maria das Dores", 4, new BigDecimal("4999.99"), LocalDate.of(1985, 8, 17)),
				new Funcionario(null, "Zé das Couves", 0, new BigDecimal("123.45"), LocalDate.of(2000, 5, 10))
			)
		);
	}

	@Override
	public Funcionario incluir(Funcionario funcionario) {
		funcionario.setId(null);
		
		return repositorio.save(funcionario);
	}

	@Override
	public Funcionario alterar(Long id, Funcionario funcionario) {
		var l = repositorio.findById(id)
						   .orElseThrow(
								   () -> new ObjectNotFoundException("Funcionário não encontrado! Id: "
										   + id + ", Tipo: " + Funcionario.class.getName(), null)
						   );
		
		// Atualiza os dados do banco com os dados vindos da requisição
		l.setNome(funcionario.getNome());
		l.setNumDep(funcionario.getNumDep());
		l.setSalario(funcionario.getSalario());
		l.setNascimento(funcionario.getNascimento());
		
		return repositorio.save(l);
	}

	@Override
	public void excluir(Long id) {
		if (repositorio.existsById(id)) {
			repositorio.deleteById(id);
		} else {
			throw new ObjectNotFoundException("Funcionário não encontrado! Id: "
					   						  + id + ", Tipo: " + Funcionario.class.getName(), null);
		}
	}

	@Override
	public List<Funcionario> listar() {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		// Recupera todos os registros da tabela
		Iterable<Funcionario> itens = repositorio.findAll();
		
		// Cria uma cópia dos dados na lista 'funcionarios'
		itens.forEach(funcionarios::add);
		
		return funcionarios;
	}

	@Override
	public Funcionario buscar(Long id) {
		Optional<Funcionario> obj = repositorio.findById(id);
				
		return obj.orElseThrow(
						() -> new ObjectNotFoundException("Funcionário não encontrado! Id: "
					  		+ id + ", Tipo: " + Funcionario.class.getName(), null)
					);
	}

	@Override
	public Funcionario buscarPorId(Long id) {
		return repositorio.findById(id).orElse(new Funcionario());					      
	}

	@Override
	public Optional<Funcionario> porId(Long id) {
		return repositorio.findById(id);
	}

	@Override
	public List<Funcionario> buscarPorNome(String nome) {
		return repositorio.findByNomeContainingIgnoreCase(nome);
	}

	@Override
	public List<Funcionario> buscarPorFaixaSalarial(BigDecimal salarioMinimo, BigDecimal salarioMaximo) {
		return repositorio.porFaixaSalarial(salarioMinimo, salarioMaximo);
	}

	@Override
	public List<Funcionario> buscarPossuiDependentes() {
		return repositorio.porPossuiDependentes();
	}
	
	@Override
	public Page<FuncionarioDTO> listarPaginado(Pageable pagina) {
		Page<Funcionario> lista = repositorio.findAll(pagina);
		Page<FuncionarioDTO> listaDTO = lista.map(funcionario -> new FuncionarioDTO(funcionario));
		
		return listaDTO;
	}
}