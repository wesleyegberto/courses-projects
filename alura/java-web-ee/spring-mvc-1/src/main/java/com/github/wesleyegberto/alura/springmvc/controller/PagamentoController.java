package com.github.wesleyegberto.alura.springmvc.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.wesleyegberto.alura.springmvc.model.CarrinhoCompras;
import com.github.wesleyegberto.alura.springmvc.model.DadosPagamento;
import com.github.wesleyegberto.alura.springmvc.model.UsuarioPrincipal;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private MailSender mailSender;
    
	@RequestMapping("/finalizar")
	public Callable<ModelAndView> finaliza(@AuthenticationPrincipal UsuarioPrincipal usuario, RedirectAttributes redirectAttrs) {
		// executa em Nonblocking
		return () -> {
			System.out.println("Inicializando cobrança de " + carrinho.getValorTotalItens());
			
			try {
		        String uri = "http://book-payment.herokuapp.com/payment";
		        String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getValorTotalItens()), String.class);
		        
		        enviaEmailPedido(usuario);
		        carrinho.esvazia();
				redirectAttrs.addFlashAttribute("mensagem", response);
				
		        return new ModelAndView("redirect:/produtos");
		    } catch (HttpClientErrorException e) {
		        e.printStackTrace();
		        if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
		        	redirectAttrs.addFlashAttribute("mensagem", "Transação não autorizada: " + e.getResponseBodyAsString());
		        } else {
		        	redirectAttrs.addFlashAttribute("mensagem", "Ocorreu um erro durante a transação: " + e.getResponseBodyAsString());
		        }
		        return new ModelAndView("redirect:/carrinho");
		    }
		};
	}

	private void enviaEmailPedido(UsuarioPrincipal usuario) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("Bookommerce - Pedido Finalizado");
		message.setFrom("noreplay@mail.com");
		message.setTo("customer@mail.com");
		message.setText("Seu pedido foi finalizado e pago com sucesso! Logo enviaremos para você =) !");
		
		mailSender.send(message);
	}
}