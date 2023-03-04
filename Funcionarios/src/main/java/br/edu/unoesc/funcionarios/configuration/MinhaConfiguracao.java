package br.edu.unoesc.funcionarios.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinhaConfiguracao {
	@Bean(name = "aplicacao")
	public String applicationName() {
		return "Sistema de Controle de Funcionários";
	}
}