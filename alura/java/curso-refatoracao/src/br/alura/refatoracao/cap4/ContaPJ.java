package br.alura.refatoracao.cap4;

public class ContaPJ extends ContaBancaria {

	private static final double TAXA_BANCARIA = 0.2;

	public ContaPJ(String titular, double saldoInicial) {
		super(titular, saldoInicial);
	}

	@Override
	public void saca(double valor) {
		super.saca(valor + TAXA_BANCARIA);
	}

	@Override
	public void deposita(double valor) {
		super.deposita(valor - TAXA_BANCARIA);
	}
}
