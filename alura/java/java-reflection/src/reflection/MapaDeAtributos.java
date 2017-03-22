package reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapaDeAtributos {

	public Map<String, Object> mapeiaAtributosValores(Object obj) throws Exception {
		Map<String, Object> atributosValores = new HashMap<>();
		Class<?> clazz = obj.getClass();
		for(Field field : clazz.getFields()) {
			field.setAccessible(true);
			atributosValores.put(field.getName(), field.get(obj));
		}
		return atributosValores;
	}
}
