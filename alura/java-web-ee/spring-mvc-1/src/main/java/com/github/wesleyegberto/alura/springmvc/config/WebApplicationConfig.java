package com.github.wesleyegberto.alura.springmvc.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.github.wesleyegberto.alura.springmvc.controller.HomeController;
import com.github.wesleyegberto.alura.springmvc.dao.ProdutoDAO;
import com.github.wesleyegberto.alura.springmvc.infra.FileSaver;
import com.github.wesleyegberto.alura.springmvc.model.CarrinhoCompras;
import com.google.common.cache.CacheBuilder;

@EnableWebMvc // ativa o WebMvc no Spring
@EnableCaching // ativa o caching
@ComponentScan( // pacotes que serão escaneados
	basePackageClasses = {
		HomeController.class, // pacote controllers
		ProdutoDAO.class, // pacote DAOs
		FileSaver.class, // pacote infra
		CarrinhoCompras.class // pacote models
	}
)
public class WebApplicationConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable(); // habilida o servlet padrão para conteúdo estático 
	}

	@Bean // produz o objeto resolutor dos caminhos da views
	public InternalResourceViewResolver internalResourceViewResolver(){
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	    resolver.setPrefix("/WEB-INF/views/");
	    resolver.setSuffix(".jsp");
	    
	    // Disponibiliza o Bean para ser acessar nas views vai EL
	    resolver.setExposedContextBeanNames("carrinhoCompras");
	    return resolver;
	}
	
	// Configuração das mensagens
	@Bean // produz o objeto com resources de mensagens
	public MessageSource messageSource() {
		// ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public ResourceBundleMessageSource resourceBundleMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		return messageSource;
	}
	
	@Bean // produz conversor de data
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        DateFormatterRegistrar registra = new DateFormatterRegistrar();
        registra.setFormatter(new DateFormatter("dd/MM/yyyy"));
        registra.registerFormatters(conversionService);
        return conversionService;
    }
	
	@Bean // produz o objeto que irá extrair os bytes do arquivo e criar o MultipartFile
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	@Bean // produz o objeto de integração REST
	public RestTemplate restTemplate() { 
		return new RestTemplate();
	}

	@Bean // produz o gerenciador de cache usando Guava
	public CacheManager cacheManager(){
	  CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
			  .maximumSize(100)
			  .expireAfterAccess(5, TimeUnit.MINUTES);
	  GuavaCacheManager manager = new GuavaCacheManager();
	  manager.setCacheBuilder(builder);
	  return manager;
	}
	
	@Bean // produz o negociador de conteúdo que usa a extensão
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager){
		// o manager recebido decide qual view será usada baseado
		// na lista de resolvers abaixo
	    List<ViewResolver> viewResolvers = new ArrayList<>();
	    viewResolvers.add(new JsonViewResolver()); // json
	    viewResolvers.add(internalResourceViewResolver()); // html ou outros que não reconheça

	    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
	    resolver.setViewResolvers(viewResolvers);
	    resolver.setContentNegotiationManager(manager);
	    return resolver;
	}
	
	// Configuração da internacionalização automática usando cookie e query param
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// muda o locale usando o query param
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		// armazena e recupera o locale do cookie
		return new CookieLocaleResolver();
	}
	
}
