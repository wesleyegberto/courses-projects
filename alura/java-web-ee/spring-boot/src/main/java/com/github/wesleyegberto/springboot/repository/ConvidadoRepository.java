package com.github.wesleyegberto.springboot.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.wesleyegberto.springboot.model.Convidado;

// Spring Data provê uma classe de CRUD tipada
public interface ConvidadoRepository extends CrudRepository<Convidado, Long> {
}
