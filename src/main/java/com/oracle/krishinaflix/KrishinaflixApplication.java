package com.oracle.krishinaflix;

import com.oracle.krishinaflix.model.DadosSeries;
import com.oracle.krishinaflix.service.ConsumirAPI;
import com.oracle.krishinaflix.service.ConverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KrishinaflixApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KrishinaflixApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumirAPI = new ConsumirAPI();
		var json = consumirAPI.obterDados("https://www.omdbapi.com/?t=friends&apikey=8cdb0b9c");
		var converterDados = new ConverterDados();
		var dados = converterDados.obterDados(json,DadosSeries.class);

		System.out.println(dados);

	}
}
