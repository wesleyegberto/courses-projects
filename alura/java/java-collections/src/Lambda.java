import java.util.ArrayList;
import java.util.List;

public class Lambda {
	public static void main(String[] args) {
		List<String> palavras = new ArrayList<>();
		palavras.add("alura online");
		palavras.add("casa do código");
		palavras.add("caelum");
		
		palavras.removeIf(palavra -> palavra.equals("alura online"));
		
		palavras.removeIf(Lambda::removerCursoAlura);
	}
	
	public static boolean removerCursoAlura(String curso) {
		return curso.equals("alura online");
	}
}
