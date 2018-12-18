package br.com.caelum.vraptor.model;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class RelatorioDeHorasTest{

    @Test
    public void calculaRelatorioQuandoDatasSaoIguais(){
        Calendar data = new GregorianCalendar(2014, 11, 11);
        HoraLancada hora1 = novaHoraLancada("10:00", "18:00", data);
        HoraLancada hora2 = novaHoraLancada("18:00", "20:00", data);
        RelatorioDeHoras relatorio = new RelatorioDeHoras(Arrays.asList(hora1, hora2));

        HorasPorDia horasPorDia = relatorio.getHorasPorDia().get(0);
        Assert.assertEquals(1, relatorio.getHorasPorDia().size());
        Assert.assertEquals(8.0, horasPorDia.getHorasNormais(), 0.001);
        Assert.assertEquals(2.0, horasPorDia.getHorasExtras(), 0.001);
        Assert.assertEquals(data, horasPorDia.getData());
    }

    @Test
    public void calculaRelatorioQuandoDatasSaoDiferentes(){
        Calendar data1 = new GregorianCalendar(2014, 10, 11);
        Calendar data2 = new GregorianCalendar(2014, 10, 12);

        HoraLancada hora1 = novaHoraLancada("10:00", "18:00", data1);
        HoraLancada hora2 = novaHoraLancada("10:00", "20:00", data2);

        RelatorioDeHoras relatorio = new RelatorioDeHoras(Arrays.asList(hora1, hora2));

        HorasPorDia horasPorDia1 = relatorio.getHorasPorDia().get(0);
        HorasPorDia horasPorDia2 = relatorio.getHorasPorDia().get(1);

        Assert.assertEquals(2, relatorio.getHorasPorDia().size());
        Assert.assertEquals(8.0, horasPorDia1.getHorasNormais(), 0.001);
        Assert.assertEquals(0.0, horasPorDia1.getHorasExtras(), 0.001);
        Assert.assertEquals(data1, horasPorDia1.getData());

        Assert.assertEquals(8.0, horasPorDia2.getHorasNormais(), 0.001);
        Assert.assertEquals(2.0, horasPorDia2.getHorasExtras(), 0.001);
        Assert.assertEquals(data2, horasPorDia2.getData());
    }

    private HoraLancada novaHoraLancada(String inicio, String fim, Calendar data){
        HoraLancada hora = new HoraLancada();
        hora.setData(data);
        hora.setHoraInicial(inicio);
        hora.setHoraFinal(fim);
        return hora;
    }
}