package com.github.wesleyegberto.alura.springmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.github.wesleyegberto.alura.springmvc.dao.UsuarioDAO;

@EnableWebSecurity // habilita o security pro Adapter
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Registra o DAO para obter o usuário
		auth.userDetailsService(usuarioDao)
			// registra o encoder
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			// Admin
			.antMatchers("/produtos/form").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
			
			// Permite os resoruces
			.antMatchers("/resources/**").permitAll()
			
			// Público
			.antMatchers(HttpMethod.GET, "/produtos", "/produtos/**").permitAll()
			.antMatchers("/carrinho/**").permitAll()
			.antMatchers("/pagamento/**").permitAll()
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and()
				// Utiliza um form de login da aplicação
				.formLogin().loginPage("/login").permitAll()
			.and()
				// configura a URL de logout
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
			
	}
}
