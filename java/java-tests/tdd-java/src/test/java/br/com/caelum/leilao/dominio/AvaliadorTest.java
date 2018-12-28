package br.com.caelum.leilao.dominio;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario jose;
	private Usuario maria;
	private Usuario odair;
	
	@Before
	public void beforeTest() {
		this.leiloeiro = new Avaliador();
		this.jose = new Usuario("Jose");
		this.maria = new Usuario("Maria");
		this.odair = new Usuario("Odair");
	}

	@Test
	public void deveAceitarLancesOrdemCrescente() {
		Leilao leilaoPc = new CriadorLeilao().para("PC da Xuxa")
				.propoe(jose, 200)
				.propoe(maria, 323)
				.propoe(odair, 450)
				.constroi();

		leiloeiro.avalia(leilaoPc);
		
		assertThat(leiloeiro.getMaiorLance(), is(450.0));
		assertThat(leiloeiro.getMenorLance(), is(200.0));
	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new CriadorLeilao().para("Playstation 3 Novo")
				.propoe(jose, 1000.0)
				.constroi();

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorLance(), is(1000.0));
		assertThat(leiloeiro.getMenorLance(), is(1000.0));
	}

	@Test
	public void deveEntenderLeilaoComLancesAleatorios() {
		Leilao leilaoPc = new CriadorLeilao().para("Playstation 4 Novo")
					.propoe(maria, 323)
					.propoe(jose, 200)
					.propoe(odair, 150)
					.propoe(jose, 600)
					.propoe(odair, 850)
					.propoe(maria, 1000)
					.propoe(jose, 1120)
					.constroi();

		leiloeiro.avalia(leilaoPc);

		assertThat(leiloeiro.getMaiorLance(), is(1120.0));
		assertThat(leiloeiro.getMenorLance(), is(150.0));
	}

	@Test
	public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
		Leilao leilao = new CriadorLeilao().para("Playstation 3 Novo")
						.propoe(jose, 400.0)
						.propoe(maria, 300.0)
						.propoe(jose, 200.0)
						.propoe(maria, 100.0)
						.constroi();
		
		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorLance(), is(400.0));
		assertThat(leiloeiro.getMenorLance(), is(100.0));
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorLeilao().para("Playstation 3 Novo")
						.propoe(jose, 100.0)
						.propoe(maria, 200.0)
						.propoe(jose, 300.0)
						.propoe(maria, 400.0)
						.constroi();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertThat(maiores.size(), is(3));
		assertThat(maiores.get(0).getValor(), is(400.0));
		assertThat(maiores.get(1).getValor(), is(300.0));
		assertThat(maiores.get(2).getValor(), is(200.0));
	}

	@Test
	public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
		Leilao leilao = new CriadorLeilao().para("Playstation 3 Novo")
						.propoe(jose, 100.0)
						.propoe(maria, 200.0)
						.constroi();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertThat(maiores.size(), is(2));
		assertThat(maiores.get(0).getValor(), is(200.0));
		assertThat(maiores.get(1).getValor(), is(100.0));
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeilaoSemLances() {
		Leilao leilao = new CriadorLeilao().para("Playstation 5 Usado").constroi();
		
		leiloeiro.avalia(leilao);
	}
}
