package br.com.alura.gerenciador.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.Empresa;
import br.com.alura.gerenciador.dao.EmpresaDAO;

public class BuscaEmpresa implements Tarefa {

	public BuscaEmpresa() {
		System.out.println("Instanciando uma tarefa do tipo BuscaEmpresa " + this);
	}

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) {
		/*
		 * PrintWriter writer = resp.getWriter(); writer.println("<html>");
		 * writer.println("<body>"); writer.println("Resultado da busca:<br/>");
		 */
		String filtro = req.getParameter("filtro");
		Collection<Empresa> empresas = new EmpresaDAO().buscaPorSimilaridade(filtro);
		/*
		 * writer.println("<ul>"); for (Empresa empresa : empresas) {
		 * writer.println("<li>" + empresa.getId() + ": " + empresa.getNome() +
		 * "</li>"); } writer.println("</ul>");
		 * 
		 * writer.println("</body>"); writer.println("</html>");
		 */
		req.setAttribute("empresas", empresas);
		// RequestDispatcher dispatcher =
		// req.getRequestDispatcher("/WEB-INF/paginas/buscaEmpresa.jsp");
		// dispatcher.forward(req, resp);
		return "/WEB-INF/paginas/buscaEmpresa.jsp";
	}

}
