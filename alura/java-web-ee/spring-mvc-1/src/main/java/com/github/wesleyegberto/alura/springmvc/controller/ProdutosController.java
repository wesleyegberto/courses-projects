package com.github.wesleyegberto.alura.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.wesleyegberto.alura.springmvc.dao.ProdutoDAO;
import com.github.wesleyegberto.alura.springmvc.infra.FileSaver;
import com.github.wesleyegberto.alura.springmvc.model.Produto;
import com.github.wesleyegberto.alura.springmvc.model.TipoPreco;
import com.github.wesleyegberto.alura.springmvc.validator.ProdutoValidator;

@Controller
@RequestMapping("produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO dao;
	
	@Autowired
	private FileSaver fileSaver;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidator());
	}
	
	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
        ModelAndView modelAndView = new ModelAndView("produtos/form");
        modelAndView.addObject("tipos", TipoPreco.values());
        return modelAndView;
    }
	
	// após o cadastro limpa o cache da home para ataulizar o novo produto
	@CacheEvict(value = "produtosHome", allEntries = true)
	@RequestMapping(method = RequestMethod.POST)
	// Para usar o Bean Validation precisamos registrar um validator do Spring para o produto
	public ModelAndView gravar(MultipartFile foto, // wrapper do arquivo upado
			@Valid Produto produto, BindingResult result, // a ordem do model e result importa
			RedirectAttributes redirectAttrs) {
	    
		if(result.hasErrors()) {
			return form(produto);
		}
		
		String caminhoRelativo = fileSaver.write("arquivos-upload", foto);
		produto.setCaminhoImagem(caminhoRelativo);
		
	    dao.salva(produto);
	    redirectAttrs.addFlashAttribute("mensagem", "Produto cadastrado com sucesso.");
	    return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listagem() {
		List<Produto> produtos = dao.lista();
        ModelAndView modelAndView = new ModelAndView("produtos/lista");
        modelAndView.addObject("listaProdutos", produtos);
        return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		Produto produto = dao.getPorId(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
	
	// devido o retorno Produto o Jackson entra em ação e serializa
	@ResponseBody // não utilizada o ViewResolver porque está explícito que é o body
	@RequestMapping("{id}")
	public Produto detalhesJson(@PathVariable("id") Long id) {
		return dao.getPorId(id);
	}
}
