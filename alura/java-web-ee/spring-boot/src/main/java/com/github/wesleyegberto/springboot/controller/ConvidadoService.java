package com.github.wesleyegberto.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wesleyegberto.springboot.model.Convidado;
import com.github.wesleyegberto.springboot.repository.ConvidadoRepository;

@Service
public class ConvidadoService {
	@Autowired
	private ConvidadoRepository repository;

	public ConvidadoService() {
	}

	public Iterable<Convidado> carregaTodos() {
		return repository.findAll();
	}

	public void salva(Convidado convidado) {
		repository.save(convidado);
	}
}