package br.com.caelum.xstream;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.SingleValueConverter;

public class PrecoSimplesConverter implements SingleValueConverter {
	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(Double.class);
	}

	@Override
	public Object fromString(String valor) {
		NumberFormat formatador = getFormatter();
		try {
			return formatador.parse(valor);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}

	@Override
	public String toString(Object value) {
		Double valor = (Double) value;
		NumberFormat formatador = getFormatter();
		String valorEmReais = formatador.format(valor);
		return valorEmReais;
	}

	private NumberFormat getFormatter() {
		Locale brasil = new Locale("pt", "br");
		NumberFormat formatador = NumberFormat.getCurrencyInstance(brasil);
		return formatador;
	}
}