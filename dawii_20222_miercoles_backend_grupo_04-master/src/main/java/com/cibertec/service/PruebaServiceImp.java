package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.entity.Prueba;
import com.cibertec.repository.PruebaRepository;

@Service
public class PruebaServiceImp implements PruebaService {

	@Autowired
	private PruebaRepository repository;

	@Override
	public Prueba insertaActualizaPrueba(Prueba obj) {
		return repository.save(obj);
	}

	@Override
	public List<Prueba> listaPrueba() {
		return repository.findAll();
	}
}
