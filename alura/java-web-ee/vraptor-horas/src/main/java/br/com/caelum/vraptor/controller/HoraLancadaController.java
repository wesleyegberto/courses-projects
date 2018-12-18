package br.com.caelum.vraptor.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.dao.HoraLancadaDAO;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.model.HoraLancada;
import br.com.caelum.vraptor.model.RelatorioDeHoras;
import br.com.caelum.vraptor.seguranca.UsuarioLogado;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class HoraLancadaController {

	private HoraLancadaDAO horaLancadaDao;

	private Validator validator;

	private Result result;

	private UsuarioLogado usuarioLogado;

	@Inject
	public HoraLancadaController(HoraLancadaDAO horaLancadaDao, Validator validator, Result result,
			UsuarioLogado usuarioLogado) {
		this.horaLancadaDao = horaLancadaDao;
		this.validator = validator;
		this.result = result;
		this.usuarioLogado = usuarioLogado;
	}

	public HoraLancadaController() {
	}

	public void form() {
	}

	@IncludeParameters
	public void adiciona(@Valid HoraLancada horaLancada) {
		validator.onErrorRedirectTo(this).form();
		horaLancada.setUsuario(usuarioLogado.getUsuario());
		horaLancadaDao.adiciona(horaLancada);
		result.redirectTo(this).lista();
	}

	public void lista() {
		result.include("horas", horaLancadaDao.lista());
	}

	public void relatorioHoras() {
		List<HoraLancada> horas = horaLancadaDao.horasDoUsuario(usuarioLogado.getUsuario());
	    RelatorioDeHoras relatorio = new RelatorioDeHoras(horas);
	    result.include("relatorio", relatorio);
	}
}