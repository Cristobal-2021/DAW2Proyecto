package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.entity.Tesis;
import com.cibertec.repository.TesisRepository;

@Service
public class TesisServiceImpl implements TesisService {
	
	@Autowired
	private TesisRepository repo;
	
	
	@Override
	public Tesis insertaActualizaTesis(Tesis obj) {
		return repo.save(obj);
	}

	@Override
	public List<Tesis> listaTesis() {
		return repo.findAll();
	}

	@Override
	public List<Tesis> listaTesisPorTituloEstadoAlumnoTema(String titulo, int estado, int idAlumno, String tema) {
		return repo.listaTesisPorTituloEstadoAlumnoTema(titulo, estado, idAlumno, tema);
	}

	@Override
	public void eliminaTesis(int idTesis) {
		repo.deleteById(idTesis);
	}

	@Override
	public List<Tesis> listaTesisPorTituloLike(String titulo) {
		return repo.listaTesisPorTituloLike(titulo);
	}

}
