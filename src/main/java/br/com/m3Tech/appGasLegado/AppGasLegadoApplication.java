package br.com.m3Tech.appGasLegado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.awt.*;

@SpringBootApplication
public class AppGasLegadoApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");

		SpringApplication.run(AppGasLegadoApplication.class, args);

		Conectar.startBd();
		ProgramaGas.CarregarConfiguracoes();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		Vendas tela = new Vendas();
		tela.setSize(d.width, d.height);
		tela.setVisible(true);
	}

}
