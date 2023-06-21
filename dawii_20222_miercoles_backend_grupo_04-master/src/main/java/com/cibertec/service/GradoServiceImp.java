package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.entity.Grado;
import com.cibertec.repository.GradoRepository;

@Service
public class GradoServiceImp implements GradoService {

	@Autowired
	private GradoRepository Repository;

	@Override
	public List<Grado> listaTodos() {
		return Repository.findAll();

	}

}
