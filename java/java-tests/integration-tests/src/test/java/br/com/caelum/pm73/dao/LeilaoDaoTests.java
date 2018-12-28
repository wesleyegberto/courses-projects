package br.com.caelum.pm73.dao;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

public class LeilaoDaoTests {
	private Session session;
	private LeilaoDao leilaoDao;
	private UsuarioDao usuarioDao;

	@Before
	public void antes() {
		session = new CriadorDeSessao().getSession();
		leilaoDao = new LeilaoDao(session);
		usuarioDao = new UsuarioDao(session);

		session.beginTransaction();
	}

	@After
	public void depois() {
		// rollback para limpar o código
		session.getTransaction().rollback();
		session.close();
	}

	@Test
	public void deveContarLeiloesNaoEncerrados() {
		// criamos um usuario
		Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		// criamos os dois leiloes
		Leilao ativo = new LeilaoBuilder().comNome("Geladeira").comDono(mauricio).comValor(1500.0).usado().constroi();
		Leilao encerrado = new LeilaoBuilder().comNome("XBox").comDono(mauricio).comValor(1500.0).encerrado()
				.constroi();

		// persistimos todos no banco
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(ativo);
		leilaoDao.salvar(encerrado);

		// invocamos a acao que queremos testar
		// pedimos o total para o DAO
		long total = leilaoDao.total();

		assertEquals(1L, total);
	}

	@Test
	public void deveRetornarLeiloesDeProdutosNovos() {
		Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		String PRODUTO_XBOX = "XBox";
		Leilao produtoNovo = new LeilaoBuilder().comNome(PRODUTO_XBOX).comDono(mauricio).comValor(1500.0).constroi();
		Leilao produtoUsado = new LeilaoBuilder().comNome("Geladeira").comDono(mauricio).comValor(1500.0).usado()
				.constroi();

		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(produtoNovo);
		leilaoDao.salvar(produtoUsado);

		List<Leilao> novos = leilaoDao.novos();

		assertEquals(1, novos.size());
		assertEquals(PRODUTO_XBOX, novos.get(0).getNome());
	}

	@Test
	public void deveTrazerSomenteLeiloesAntigos() {
		Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		Leilao recente = new LeilaoBuilder().comNome("XBox").comDono(mauricio).comValor(1500.0).usado().constroi();
		String PRODUTO_GELADEIRA = "Geladeira";
		Leilao antigo = new LeilaoBuilder().comNome(PRODUTO_GELADEIRA).comDono(mauricio).comValor(1500.0).diasAtras(10)
				.usado().constroi();

		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(recente);
		leilaoDao.salvar(antigo);

		List<Leilao> antigos = leilaoDao.antigos();

		assertEquals(1, antigos.size());
		assertEquals(PRODUTO_GELADEIRA, antigos.get(0).getNome());
	}

	@Test
	public void deveTrazerSomenteLeiloesAntigosHaMaisDe7Dias() {
		Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		Leilao noLimite = new LeilaoBuilder().comNome("Geladeira").comDono(mauricio).comValor(1500.0).diasAtras(7)
				.constroi();

		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(noLimite);

		List<Leilao> antigos = leilaoDao.antigos();

		assertEquals(1, antigos.size());
	}

	@Test
	public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {
		Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		// criando os leiloes, cada um com uma data
		final String PRODUTO_XBOX = "XBox";
		Leilao leilao1 = new LeilaoBuilder().comNome(PRODUTO_XBOX).comDono(mauricio).comValor(1500.0).diasAtras(2)
				.constroi();
		Leilao leilao2 = new LeilaoBuilder().comNome("Geladeira").comDono(mauricio).comValor(1500.0).diasAtras(20)
				.constroi();

		// persistindo os objetos no banco
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);

		Calendar comecoDoIntervalo = Calendar.getInstance();
		comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
		Calendar fimDoIntervalo = Calendar.getInstance();
		// invocando o metodo para testar
		List<Leilao> leiloes = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

		// garantindo que a query funcionou
		assertEquals(1, leiloes.size());
		assertEquals(PRODUTO_XBOX, leiloes.get(0).getNome());
	}

	@Test
	public void naoDeveTrazerLeiloesEncerradosNoPeriodo() {
		Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		// criando os leiloes, cada um com uma data
		Leilao leilao1 = new LeilaoBuilder().comNome("XBox").comDono(mauricio).comValor(1500.0).diasAtras(2).encerrado()
				.constroi();

		// persistindo os objetos no banco
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(leilao1);

		Calendar comecoDoIntervalo = Calendar.getInstance();
		comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
		Calendar fimDoIntervalo = Calendar.getInstance();

		// invocando o metodo para testar
		List<Leilao> leiloes = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

		// garantindo que a query funcionou
		assertEquals(0, leiloes.size());
	}

	@Test
	public void deveRetornarLeiloesDisputados() {
		Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");
		Usuario marcelo = new Usuario("Marcelo", "marcelo@aniche.com.br");

		Leilao leilao1 = new LeilaoBuilder().comDono(marcelo).comValor(3000.0)
				.comLance(Calendar.getInstance(), mauricio, 3000.0).comLance(Calendar.getInstance(), marcelo, 3100.0)
				.constroi();

		Leilao leilao2 = new LeilaoBuilder().comDono(mauricio).comValor(3200.0)
				.comLance(Calendar.getInstance(), mauricio, 3000.0).comLance(Calendar.getInstance(), marcelo, 3100.0)
				.comLance(Calendar.getInstance(), mauricio, 3200.0).comLance(Calendar.getInstance(), marcelo, 3300.0)
				.comLance(Calendar.getInstance(), mauricio, 3400.0).comLance(Calendar.getInstance(), marcelo, 3500.0)
				.constroi();

		usuarioDao.salvar(marcelo);
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);

		List<Leilao> leiloes = leilaoDao.disputadosEntre(2500, 3500);

		assertEquals(1, leiloes.size());
		assertEquals(3200.0, leiloes.get(0).getValorInicial(), 0.00001);
	}

	@Test
	public void listaSomenteOsLeiloesDoUsuario() throws Exception {
		Usuario dono = new Usuario("Mauricio", "m@a.com");
		Usuario comprador = new Usuario("Victor", "v@v.com");
		Usuario comprador2 = new Usuario("Guilherme", "g@g.com");
		Leilao leilao = new LeilaoBuilder().comDono(dono).comValor(50.0)
				.comLance(Calendar.getInstance(), comprador, 100.0).comLance(Calendar.getInstance(), comprador2, 200.0)
				.constroi();
		Leilao leilao2 = new LeilaoBuilder().comDono(dono).comValor(250.0)
				.comLance(Calendar.getInstance(), comprador2, 100.0).constroi();
		usuarioDao.salvar(dono);
		usuarioDao.salvar(comprador);
		usuarioDao.salvar(comprador2);
		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);

		List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
		assertEquals(1, leiloes.size());
		assertEquals(leilao, leiloes.get(0));
	}

	@Test
	public void listaDeLeiloesDeUmUsuarioNaoTemRepeticao() throws Exception {
		Usuario dono = new Usuario("Mauricio", "m@a.com");
		Usuario comprador = new Usuario("Victor", "v@v.com");
		Leilao leilao = new LeilaoBuilder().comDono(dono).comLance(Calendar.getInstance(), comprador, 100.0)
				.comLance(Calendar.getInstance(), comprador, 200.0).constroi();
		usuarioDao.salvar(dono);
		usuarioDao.salvar(comprador);
		leilaoDao.salvar(leilao);

		List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
		assertEquals(1, leiloes.size());
		assertEquals(leilao, leiloes.get(0));
	}

	@Test
	public void devolveAMediaDoValorInicialDosLeiloesQueOUsuarioParticipou() {
		Usuario dono = new Usuario("Mauricio", "m@a.com");
		Usuario comprador = new Usuario("Victor", "v@v.com");
		Leilao leilao = new LeilaoBuilder().comDono(dono).comValor(50.0)
				.comLance(Calendar.getInstance(), comprador, 100.0).comLance(Calendar.getInstance(), comprador, 200.0)
				.constroi();
		Leilao leilao2 = new LeilaoBuilder().comDono(dono).comValor(250.0)
				.comLance(Calendar.getInstance(), comprador, 100.0).constroi();
		usuarioDao.salvar(dono);
		usuarioDao.salvar(comprador);
		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);

		assertEquals(150.0, leilaoDao.getValorInicialMedioDoUsuario(comprador), 0.001);
	}

	@Test
	public void deveDeletarUmUsuario() {
		Usuario usuario = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		usuarioDao.salvar(usuario);
		usuarioDao.deletar(usuario);
		
		// Garantir que os comandos seguem ao banco - evitar cache
		session.flush();
		session.clear();

		Usuario usuarioNoBanco = usuarioDao.porNomeEEmail("Mauricio Aniche", "mauricio@aniche.com.br");

		assertNull(usuarioNoBanco);

	}
}
