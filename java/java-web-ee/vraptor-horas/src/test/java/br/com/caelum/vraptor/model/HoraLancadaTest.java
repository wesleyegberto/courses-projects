package br.com.caelum.vraptor.model;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.vraptor.model.HoraLancada;

public class HoraLancadaTest {
    @Test
    public void calculaDuracaoQuandoInicioMenorDoQueFim() throws Exception {
        HoraLancada hora = new HoraLancada();

        hora.setHoraInicial("09:00");
        hora.setHoraFinal("19:00");

        Assert.assertEquals(10.0, hora.getDuracao(), 0.01);
    }
}
