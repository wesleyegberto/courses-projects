package com.airhacks.testdriver;

import com.airhacks.weather.business.forecast.boundary.WeatherService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author airhacks.com
 */
@WebServlet(name = "TestDriver", urlPatterns = { "/stress" })
public class TestDriver extends HttpServlet {
	private static final long serialVersionUID = -6949606983710422833L;

	@Inject
	WeatherService service;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("From test driver: " + service.forecast());
		}
	}
}
