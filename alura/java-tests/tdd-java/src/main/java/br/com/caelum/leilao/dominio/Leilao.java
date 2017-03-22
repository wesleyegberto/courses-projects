package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {
		if (lances.isEmpty() || podeDarLance(lance.getUsuario())) {
			lances.add(lance);
		}
	}
	
	public void dobraLance(Usuario usuario) {
		if(lances.isEmpty()) return;
		Lance ultimoLance = getUltimoLanceDo(usuario);
		if(ultimoLance != null) {
			propoe(new Lance(usuario, ultimoLance.getValor() * 2));
		}
	}
	
	private Lance getUltimoLanceDo(Usuario usuario) {
		for(int i = lances.size() - 1; i >= 0; i--) {
			Lance lance = lances.get(i);
			if(lance.getUsuario().equals(usuario)) {
				return lance;
			}
		}
		return null;
	}

	private boolean podeDarLance(Usuario usuario) {
		int totalLances = getQtdeLancesDo(usuario);
		return !getUltimoLanceDado().getUsuario().equals(usuario) && totalLances < 5;
	}

	private int getQtdeLancesDo(Usuario usuario) {
		int total = 0;
		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario))
				total++;
		}
		return total;
	}

	private Lance getUltimoLanceDado() {
		if (lances.isEmpty())
			return null;
		return lances.get(lances.size() - 1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}
}
