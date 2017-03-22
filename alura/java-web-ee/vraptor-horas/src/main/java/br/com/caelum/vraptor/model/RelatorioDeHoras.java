package br.com.caelum.vraptor.model;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class RelatorioDeHoras {

    private List<HorasPorDia> horasPorDia = new ArrayList<>();
	private double totalHorasNormais;
	private double totalHorasExtras;

    public RelatorioDeHoras(List<HoraLancada> horas) {
        calculaHorasPorDia(horas);
    }

    private <T> void calculaHorasPorDia(List<HoraLancada> horas) {
        if (!horas.isEmpty()) {
            /*
        	double horasDoDia = 0.0;
            Calendar dataAtual = horas.get(0).getData();
            for (HoraLancada hora : horas) {
                if(!hora.getData().equals(dataAtual)){
                    adicionaHoraPorDia(horasDoDia, dataAtual);
                    dataAtual = hora.getData();
                    horasDoDia = 0.0;
                }
                horasDoDia += hora.getDuracao();
            }
            adicionaHoraPorDia(horasDoDia, dataAtual);
            */
        	

			horasPorDia = horas.stream()
				.collect(
					// Transforma a Data e o total daquela data numa instância de HorasPorDia
					collectingAndThen(
						// Agrupa os totais de horas por dia e instância HorasPorDia com as horas
						groupingBy(HoraLancada::getData, reducing(0.0, HoraLancada::getDuracao, Double::sum)),
						// A partir do <Calendar, Double> instancia um HorasPorDia
						m -> m.entrySet().stream().map(e -> criaHorasPorDia(e.getKey(), e.getValue()))
					)
				)
				.sorted(Comparator.comparing(HorasPorDia::getData))
				.collect(toList());
			
            totalHorasNormais = horasPorDia.stream().mapToDouble(HorasPorDia::getHorasNormais).sum();
            totalHorasExtras = horasPorDia.stream().mapToDouble(HorasPorDia::getHorasExtras).sum();
        }
    }
    /*
    private static HorasPorDia criaHorasPorDia(double totalHorasDoDia) {
    	return new HorasPorDia(calculaHorasNormais(totalHorasDoDia), calculaHorasExtras(totalHorasDoDia), null);
    }
	*/
    private static HorasPorDia criaHorasPorDia(Calendar dataLancada, double totalHorasDoDia) {
    	return new HorasPorDia(calculaHorasNormais(totalHorasDoDia), calculaHorasExtras(totalHorasDoDia), dataLancada);
    }
	
	private static double calculaHorasNormais(double duracao) {
		return Math.min(duracao, 8);
	}
	
	private static double calculaHorasExtras(double duracao) {
		return Math.max(duracao - 8, 0.0);
	}
	/*
	private void adicionaHoraPorDia(double horasDoDia, Calendar dataAtual) {
		horasPorDia.add(new HorasPorDia(calculaHorasNormais(horasDoDia), calculaHorasExtras(horasDoDia), dataAtual));
	}
	*/

    public List<HorasPorDia> getHorasPorDia() {
        return horasPorDia;
    }

    public double getTotalHorasNormais() {
        return totalHorasNormais;
    }

    public double getTotalHorasExtras() {
        return totalHorasExtras;
    }
}