package com.github.wesleyegberto.alura.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.wesleyegberto.alura.springmvc.dao.ProdutoDAO;
import com.github.wesleyegberto.alura.springmvc.model.Produto;

@Controller
public class HomeController {

	@Autowired
	private ProdutoDAO produtoDao;
	
	// cache o resultado usando a key produtosHome
	@Cacheable(value = "produtosHome")
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("home");
		List<Produto> produtos = produtoDao.lista();
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
}
