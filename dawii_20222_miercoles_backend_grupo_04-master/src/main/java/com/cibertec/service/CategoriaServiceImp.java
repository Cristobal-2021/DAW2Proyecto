package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.entity.Categoria;
import com.cibertec.repository.CategoriaRepository;

@Service
public class CategoriaServiceImp implements CategoriaService {

	@Autowired
	private CategoriaRepository Repository;

	@Override
	public List<Categoria> listaTodos() {
		return Repository.findAll();

	}

}
