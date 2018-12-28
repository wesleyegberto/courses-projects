package br.com.caelum.leilao.dominio;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LeilaoTest {

    private Usuario steveJobs;
	private Usuario billGates;

	@Before
	public void beforeTest() {
        this.steveJobs = new Usuario("Steve Jobs");
        this.billGates = new Usuario("Bill Gates");
	}
	
	@Test
    public void deveReceberUmLance() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));

        assertThat(leilao.getLances().size(), is(1));
        assertThat(leilao.getLances().get(0).getValor(), is(2000.0));
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new CriadorLeilao().para("Macbook Pro 15")
        				.propoe(steveJobs, 2000.0)
        				.propoe(billGates, 3000.0)
        				.constroi();

        assertThat(leilao.getLances().size(), is(2));
        assertThat(leilao.getLances().get(0).getValor(), is(2000.0));
        assertThat(leilao.getLances().get(1).getValor(), is(3000.0));
    }
    
    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
        Leilao leilao = new CriadorLeilao().para("Macbook Pro 15")
				.propoe(steveJobs, 2000.0)
				.propoe(billGates, 3000.0)
				.propoe(steveJobs, 4000.0)
				.propoe(billGates, 5000.0)
				.propoe(steveJobs, 6000.0)
				.propoe(billGates, 7000.0)
				.propoe(steveJobs, 8000.0)
				.propoe(billGates, 9000.0)
				.propoe(steveJobs, 10000.0)
				.propoe(billGates, 11000.0)
				.constroi();

        // deve ser ignorado
        leilao.propoe(new Lance(steveJobs, 12000));

        assertThat(leilao.getLances().size(), is(10));
        int ultimo = leilao.getLances().size() - 1;
        Lance ultimoLance = leilao.getLances().get(ultimo);
        assertThat(ultimoLance.getValor(), is(11000.0));
    }
    
    @Test
    public void deveDobrarOLanceAnterior() {
        Leilao leilao = new CriadorLeilao().para("Macbook Pro 15")
				.propoe(steveJobs, 2000.0)
				.propoe(billGates, 3000.0)
				.constroi();
        
        leilao.dobraLance(steveJobs);
        
        assertThat(leilao.getLances().size(), is(3));
        int ultimo = leilao.getLances().size() - 1;
        Lance ultimoLance = leilao.getLances().get(ultimo);
        assertThat(ultimoLance.getValor(), is(4000.0));
    }
    
    @Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new Leilao("Macbook Pro 15");

        leilao.dobraLance(steveJobs);

        assertThat(leilao.getLances().size(), is(0));
    }
}