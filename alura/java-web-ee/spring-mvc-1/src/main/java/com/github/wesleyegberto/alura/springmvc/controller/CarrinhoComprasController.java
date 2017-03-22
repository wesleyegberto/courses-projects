package com.github.wesleyegberto.alura.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.github.wesleyegberto.alura.springmvc.dao.ProdutoDAO;
import com.github.wesleyegberto.alura.springmvc.model.CarrinhoCompras;
import com.github.wesleyegberto.alura.springmvc.model.CarrinhoItem;
import com.github.wesleyegberto.alura.springmvc.model.Produto;
import com.github.wesleyegberto.alura.springmvc.model.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private CarrinhoCompras carrinho;

	private CarrinhoItem criaItem(Long produtoId, TipoPreco tipo){
	    Produto produto = produtoDao.getPorId(produtoId);
	    CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipo);
	    return carrinhoItem;
	}
	
	@RequestMapping("/add")
	public ModelAndView add(Long produtoId, TipoPreco tipoSelec) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		CarrinhoItem carrinhoItem = criaItem(produtoId, tipoSelec);
		carrinho.add(carrinhoItem);
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String carrinho() {
		return "carrinho/itens";
	}
	
	@RequestMapping("/remover")
	public ModelAndView remover(Long produtoId, TipoPreco tipoPreco){
	    carrinho.remove(produtoId, tipoPreco);
	    return new ModelAndView("redirect:/carrinho");
	}
}