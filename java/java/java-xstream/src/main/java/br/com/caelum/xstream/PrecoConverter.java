package br.com.caelum.xstream;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PrecoConverter implements Converter {

	private NumberFormat getFormatter() {
		Locale brasil = new Locale("pt","br");
		NumberFormat formatador = NumberFormat.getCurrencyInstance(brasil);
		return formatador;
	}
	
	@Override
	public boolean canConvert(Class type) {
	    return type.isAssignableFrom(Double.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext ctx) {
		Double valor = (Double) value;
		NumberFormat formatador = getFormatter();
		String valorEmReais = formatador.format(valor);
		writer.setValue(valorEmReais);
	}


	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {
		String valor = reader.getValue();
		NumberFormat formatador = getFormatter();
		try {
		    return formatador.parse(valor);
		} catch (ParseException e) {
		    throw new ConversionException(e);
		}
	}

}
