package com.oracle.krishinaflix.principal;

import com.oracle.krishinaflix.model.DadosEpisodio;
import com.oracle.krishinaflix.model.DadosSeries;
import com.oracle.krishinaflix.model.DadosTemporada;
import com.oracle.krishinaflix.service.ConsumirAPI;
import com.oracle.krishinaflix.service.ConverterDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumirAPI consumirAPI = new ConsumirAPI();
    private ConverterDados converterDados = new ConverterDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8cdb0b9c";


    public void exibirMenu(){
        System.out.println("Digite o nome da Série para buscar");
        var nomeSerie = scanner.nextLine();
        var json = consumirAPI.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);
        var dadosSeries = converterDados.obterDados(json, DadosSeries.class);
        System.out.println(dadosSeries);


        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSeries.totalTemporadas(); i++){


            json = consumirAPI.obterDados(ENDERECO + nomeSerie.replace(" ","+") + "&season="+i+ API_KEY);
            DadosTemporada dadosTemporada = converterDados.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);

//        for (int i = 0; i < dadosSeries.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodiosList();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodiosList().forEach(e -> System.out.println(e.titulo())));

    }

}
