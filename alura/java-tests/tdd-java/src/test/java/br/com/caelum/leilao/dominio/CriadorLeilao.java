package br.com.caelum.leilao.dominio;

public class CriadorLeilao {

	private Leilao leilao;

	public CriadorLeilao para(String objetoLeilao) {
		this.leilao = new Leilao(objetoLeilao);
		return this;
	}

	public CriadorLeilao propoe(Usuario jose, double valorLance) {
		this.leilao.propoe(new Lance(jose, valorLance));
		return this;
	}

	public Leilao constroi() {
		return leilao;
	}

}
