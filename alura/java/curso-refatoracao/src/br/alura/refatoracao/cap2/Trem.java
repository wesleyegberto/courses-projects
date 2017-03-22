package br.alura.refatoracao.cap2;

import java.util.List;

public class Trem {
	private List<Vagao> vagoes;
	private int capacidade;
	
	public boolean podeReservar(int qtdeLugares) {
		int qtdeLugaresDisponiveis = capacidade - calculaQtdeLugaresReservados();
		return qtdeLugaresDisponiveis > qtdeLugares; 
	}

	private int calculaQtdeLugaresReservados() {
		int qtdeLugaresReservados = 0;
		for(Vagao vagao : vagoes) {
			qtdeLugaresReservados += vagao.reservados();
		}
		return qtdeLugaresReservados;
	}
}