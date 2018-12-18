package br.com.caelum.vraptor.model;

import java.util.Calendar;

public class HorasPorDia {

	private double horasNormais;
	private double horasExtras;
	private Calendar data;

	public HorasPorDia(double horasNormais, double horasExtras, Calendar data) {
		this.horasNormais = horasNormais;
		this.horasExtras = horasExtras;
		this.data = data;
	}

	public double getHorasNormais() {
		return horasNormais;
	}

	public void setHorasNormais(double horasNormais) {
		this.horasNormais = horasNormais;
	}

	public double getHorasExtras() {
		return horasExtras;
	}

	public void setHorasExtras(double horasExtras) {
		this.horasExtras = horasExtras;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "HorasPorDia [horasNormais=" + horasNormais + ", horasExtras=" + horasExtras + ", data=" + data + "]";
	}
	
	

}