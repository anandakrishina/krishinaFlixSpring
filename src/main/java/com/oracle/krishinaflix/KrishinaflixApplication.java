package com.oracle.krishinaflix;

import com.oracle.krishinaflix.model.DadosEpisodio;
import com.oracle.krishinaflix.model.DadosSeries;
import com.oracle.krishinaflix.model.DadosTemporada;
import com.oracle.krishinaflix.principal.Principal;
import com.oracle.krishinaflix.service.ConsumirAPI;
import com.oracle.krishinaflix.service.ConverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class KrishinaflixApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KrishinaflixApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


			// código para filtragem.

        Principal principal = new Principal();
        principal.exibirMenu();



	}
}
