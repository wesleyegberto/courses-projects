package com.github.wesleyegberto.alura.springmvc.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
			JPAFactoryConfig.class, // configuração do JPA
			WebApplicationConfig.class, // configuração da o MVC
			WebSecurityConfig.class // inicialização do Spring Security
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodeFilter = new CharacterEncodingFilter();
		encodeFilter.setEncoding("UTF-8");
		return new Filter[] {
			encodeFilter,
			// new OpenEntityManagerInViewFilter() Pattern OpenSessionInView
		};
	}

	// usando profile precisamos ativar o default em DESENV
	// Em produção devemos usar -Dspring.profile
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
	    super.onStartup(servletContext);
	    servletContext.addListener(new RequestContextListener());
	    servletContext.setInitParameter("spring.profiles.active", "dev");
	}
	
	@Override // registra o handle de arquivos
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

}
