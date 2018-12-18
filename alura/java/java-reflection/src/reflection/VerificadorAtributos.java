package reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VerificadorAtributos {
	public static List<String> verificaCampos(Object obj) {
		List<String> camposNulos = new ArrayList<String>();
		try {
			Class<?> clazz = obj.getClass();
			for(Field field : clazz.getFields()) {
				if(field.get(obj) == null) {
					camposNulos.add(field.getName());
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return camposNulos;
	}

	public static List<String> pesquisaStringEmAtributos(Object obj, String valor) {
		Objects.requireNonNull(valor);
		List<String> camposNulos = new ArrayList<String>();
		try {
			Class<?> clazz = obj.getClass();
			for(Field field : clazz.getFields()) {
				Object valorCampo = field.get(obj);
				if(valorCampo != null && valor.equals(valorCampo.toString())) {
					camposNulos.add(field.getName());
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return camposNulos;
	}
}
