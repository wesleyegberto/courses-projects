package reflection;

import java.lang.reflect.Constructor;
import java.util.Map;

public class MapaDeClasses {

	private Map<String, String> mapa;

	public Class getClass(String chave) throws Exception {
		String valor = mapa.get(chave);
		if (valor != null) {
			return Class.forName(valor);
		} else {
			throw new RuntimeException("Chave inválida");
		}
	}

	public Object getInstancia(String chave) throws Exception {
		return getClass(chave).newInstance();
	}

	public Object getInstancia(String chave, Object[] params) throws Exception {
		Class clazz = getClass(chave);
		Class<?>[] tiposParams = new Class<?>[params.length];
		for (int i = 0; i < tiposParams.length; i++) {
			tiposParams[i] = params[i].getClass();
		}
		Constructor<?> constructor = clazz.getConstructor(tiposParams);
		return constructor.newInstance(params);
	}
}