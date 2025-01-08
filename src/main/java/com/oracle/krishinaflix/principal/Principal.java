package com.oracle.krishinaflix.principal;

import com.oracle.krishinaflix.model.DadosEpisodio;
import com.oracle.krishinaflix.model.DadosSeries;
import com.oracle.krishinaflix.model.DadosTemporada;
import com.oracle.krishinaflix.model.Episodios;
import com.oracle.krishinaflix.service.ConsumirAPI;
import com.oracle.krishinaflix.service.ConverterDados;
import org.springframework.cglib.core.Local;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        //temporadas.forEach(System.out::println);

//        for (int i = 0; i < dadosSeries.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodiosList();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        //temporadas.forEach(t -> t.episodiosList().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodioList = temporadas.stream()
                .flatMap(t -> t.episodiosList().stream())
                .collect(Collectors.toList());

        //.toList() -> Gera uma lista que não pode ser modificada
//
        dadosEpisodioList.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e-> System.out.println("Filtered value: " + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e-> System.out.println("Sorted value: " + e))
                .limit(10)
                .map(e-> e.titulo().toUpperCase())
                .peek(e-> System.out.println("Mapped value: " + e))
                .forEach(System.out::println);

        System.out.println(dadosEpisodioList);

        List<Episodios> episodios = temporadas.stream()
                .flatMap(t -> t.episodiosList().stream()
                        .map(d -> new Episodios(t.numero(), d)))
                .collect(Collectors.toList());

        //episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios?");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano,1,1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada()
                                + " Episódio: " + e.getTitulo()
                                + " Data de lançamento: " + e.getDataLancamento().format(dateTimeFormatter) + "\n"
                ));

        System.out.println("Digite o título: ");
        var trechoTitulo = scanner.nextLine();
        Optional<Episodios> first = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        System.out.println(first);

        var firstOutro = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .collect(Collectors.toList());

        firstOutro.forEach(System.out::println);
    }

}
