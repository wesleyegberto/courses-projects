package com.github.wesleyegberto.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.wesleyegberto.springboot.model.Convidado;

@Controller
public class ConvidadoController {

	@Autowired
	private ConvidadoService service;

	@RequestMapping("/")
	public String home() {
		// O padrão do Thymeleaf já vai para /templates/index.html
		return "index";
	}

	@RequestMapping("convidados")
	public String listaConvidados(Model model) {
		Iterable<Convidado> convidados = service.carregaTodos();
		model.addAttribute("convidados", convidados);
		return "convidados";
	}
	
	@RequestMapping(value =  "salva", method = RequestMethod.POST)
	public String salvaConvidado(Convidado convidado, Model model) {
		service.salva(convidado);
		
		return "redirect:convidados";
	}
}
