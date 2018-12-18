package br.com.caelum.pm73.dao;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoIT {

	private Session session;
	private UsuarioDao usuarioDao;

	@Before
	public void init() {
		session = new CriadorDeSessao().getSession();
		usuarioDao = new UsuarioDao(session);

		session.beginTransaction();
	}

	@After
	public void finish() {
		session.getTransaction().rollback();
		session.close();
	}

	@Test
	public void deveAlterarUmUsuario() {
		Usuario usuario = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		usuarioDao.salvar(usuario);

		usuario.setNome("João da Silva");
		usuario.setEmail("joao@silva.com.br");

		usuarioDao.atualizar(usuario);

		session.flush();

		Usuario novoUsuario = usuarioDao.porNomeEEmail("João da Silva", "joao@silva.com.br");
		assertNotNull(novoUsuario);
		System.out.println(novoUsuario);

		Usuario usuarioInexistente = usuarioDao.porNomeEEmail("Mauricio Aniche", "mauricio@aniche.com.br");
		assertNull(usuarioInexistente);

	}

	@Test
	public void deveEncontrarPeloNomeEEmail() {
		// criando um usuario e salvando antes de invocar o porNomeEEmail
		Usuario novoUsuario = new Usuario("João da Silva", "joao@dasilva.com.br");
		usuarioDao.salvar(novoUsuario);

		// agora buscamos no banco
		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("João da Silva", "joao@dasilva.com.br");

		assertEquals("João da Silva", usuarioDoBanco.getNome());
		assertEquals("joao@dasilva.com.br", usuarioDoBanco.getEmail());
	}

	@Test
	public void deveRetornarNuloSeNaoEncontrarUsuario() {
		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("João Joaquim", "joao@joaquim.com.br");

		assertNull(usuarioDoBanco);
	}
}
