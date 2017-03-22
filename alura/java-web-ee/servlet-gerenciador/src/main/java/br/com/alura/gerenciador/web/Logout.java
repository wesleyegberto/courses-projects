package br.com.alura.gerenciador.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout implements Tarefa {
	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) {
		/*
		 * Cookie cookie = new Cookies(req.getCookies()).getUsuarioLogado();
		 * 
		 * if (cookie != null) { cookie.setMaxAge(0); resp.addCookie(cookie); }
		 */
		req.getSession().invalidate();
		// PrintWriter writer = resp.getWriter();
		// writer.println("<html><body>Logout efetuado</body></html>");
	    //RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/paginas/logout.html");
	    //dispatcher.forward(req, resp);

        return "/WEB-INF/paginas/logout.html";
	}
}