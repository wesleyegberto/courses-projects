package br.com.casadocodigo.loja.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.Filter;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.wesleyegberto.alura.springmvc.config.JPAFactoryConfig;
import com.github.wesleyegberto.alura.springmvc.config.WebApplicationConfig;
import com.github.wesleyegberto.alura.springmvc.config.WebSecurityConfig;

import br.com.casadocodigo.loja.config.DataSourceConfigurationTest;

// Rodar no container de test do Spring
@RunWith(SpringJUnit4ClassRunner.class)
// habilitar e carregas as configurações do SpringMvc (inclusive o context)
@WebAppConfiguration
//Carrega as classes de configuração para inicar o contexto
@ContextConfiguration(classes = {
	WebApplicationConfig.class, // usar as configurações da aplicação
	JPAFactoryConfig.class,
	DataSourceConfigurationTest.class,
	WebSecurityConfig.class // testar as regras de segurança
})
//Profile de test para carregar configurações específicas
@ActiveProfiles("test")
public class ProdutosControllerTest {
	// injeta o contexto da aplicação para criar o mock
	@Autowired
    private WebApplicationContext wac;

	// injeta o filtro de segurança do Spring
    @Autowired
    private Filter springSecurityFilterChain;

	// mock a infra do SpringMvc
    private MockMvc mockMvc;

	// mock a infra do SpringMvc com Spring Security
	private MockMvc mockMvcSecurity;

    @Before
    public void setup() {
    	// cria o mock que efetuará os requests nos controllers
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        // cria o mock com filtro do Spring Secutiry
        mockMvcSecurity = MockMvcBuilders.webAppContextSetup(wac).addFilter(springSecurityFilterChain).build();
    }
    
    @Test
    public void deveRetornarParaHomeComOsLivros() throws Exception{
    	// Efetua o GET
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
        	.andExpect(status().is(200))
        	// assert do atributo do request enviado para a view
        	.andExpect(model().attributeExists("produtos"))
        	// assert a view retornada pelo controller sem resolução do ViewResolver
        	.andExpect(view().name("home"))
    		// assert a view retornada pelo controller após resolução do ViewResolver
            //.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/home.jsp"))
            ;
    }
    
    @Test
    public void deveNegarAcessoNaPaginaProdutosFormParaUsuarioComum() throws Exception{
    	mockMvcSecurity.perform(MockMvcRequestBuilders.get("/produtos/form")
    			// Mocka um Principal de um Usuário Comum
                .with(SecurityMockMvcRequestPostProcessors
                    .user("user@casadocodigo.com.br").password("123456")
                    .roles("USUARIO")))
                .andExpect(status().is(403));
    }

    @Test
    public void devePermitirAcessoNaPaginaProdutosFormParaAdmin() throws Exception{
    	mockMvcSecurity.perform(MockMvcRequestBuilders.get("/produtos/form")
    			// Mocka um Principal de Administrador
                .with(SecurityMockMvcRequestPostProcessors
                    .user("admin@casadocodigo.com.br").password("123456")
                    .roles("ADMIN")))
                .andExpect(status().is(200));
    }
}