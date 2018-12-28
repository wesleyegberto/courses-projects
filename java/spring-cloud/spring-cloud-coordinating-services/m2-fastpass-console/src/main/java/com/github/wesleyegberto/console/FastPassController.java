package com.github.wesleyegberto.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class FastPassController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(path="/customerdetails", params={"fastpassid"})
	public String getFastPassCustomerDetails(@RequestParam String fastpassid, Model m) {
		
		// RestTemplate rest = new RestTemplate();
		// FastPassCustomer c = rest.getForObject("http://localhost:8086/fastpass?fastpassid=" + fastpassid, FastPassCustomer.class);
		
		FastPassCustomer c = restTemplate.getForObject("http://fastpass-service/fastpass?fastpassid=" + fastpassid, FastPassCustomer.class);
		System.out.println("retrieved customer details");
		m.addAttribute("customer", c);
		return "console";
	}

}
