package br.com.alura.gerenciador.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.Usuario;
import br.com.alura.gerenciador.dao.UsuarioDAO;

public class Login implements Tarefa {
	public String executa(HttpServletRequest req, HttpServletResponse resp) {
		// PrintWriter writer = resp.getWriter();
		String email = req.getParameter("email");
		String senha = req.getParameter("senha");

		Usuario usuario = new UsuarioDAO().buscaPorEmailESenha(email, senha);

		if (usuario == null) {
			// writer.println("<html><body>Usuário ou senha inválida</body></html>");
			return "/WEB-INF/paginas/senhaInvalida.html";
		} else {
			/*
			Cookie cookie = new Cookie("usuario.logado", email);
			resp.addCookie(cookie);
			*/
			req.getSession().setAttribute("usuarioLogado", usuario);;
			req.setAttribute("email", usuario.getEmail());
			// writer.println("<html><body>Usuário logado: " + email + "</body></html>");
			return "/WEB-INF/paginas/usuarioLogado.jsp";
		}
	}

}