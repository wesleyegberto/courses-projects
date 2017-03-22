package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvocadorTeste {
	public void invocaTestesLancandoThrowable(Object obj) throws Throwable {
		Class<?> clazz = obj.getClass();
		for (Method m : clazz.getMethods()) {
			if (m.getName().startsWith("test") && m.getReturnType() == Void.class && m.getParameters().length == 0) {
				try {
					m.invoke(obj);
				} catch (InvocationTargetException ex) {
					throw ex.getTargetException();
				}
			}
		}
	}
	
	public void invocaTestes(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		for (Method m : clazz.getMethods()) {
			if (m.getName().startsWith("test") && m.getReturnType() == Void.class && m.getParameters().length == 0) {
				m.invoke(obj);
			}
		}
	}
}
