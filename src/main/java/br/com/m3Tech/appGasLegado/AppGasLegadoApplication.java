package br.com.m3Tech.appGasLegado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import programagas.Conectar;
import programagas.ProgramaGas;
import programagas.Vendas;

@SpringBootApplication
public class AppGasLegadoApplication {

	public static void main(String[] args) {

		SpringApplication.run(AppGasLegadoApplication.class, args);

		Conectar.startBd();
		ProgramaGas.CarregarConfiguracoes();
		Vendas.main(args);
	}

}
