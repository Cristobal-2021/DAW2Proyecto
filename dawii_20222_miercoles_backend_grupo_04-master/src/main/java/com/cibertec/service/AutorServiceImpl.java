package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.entity.Autor;
import com.cibertec.repository.AutorRepository;
@Service
public class AutorServiceImpl implements AutorService {

	@Autowired
	AutorRepository repoA;

	@Override
	public List<Autor> listaAutorNombreTelefonoEstadoGrado(String nombre, String apellido, String telefono, int estado,
			int idGrado) {

		return repoA.listaAutorNombreTelefonoEstadoGrado(nombre, apellido, telefono, estado, idGrado);
	}

	@Override
	public Autor insertarActualizarAutor(Autor obj) {
		return repoA.save(obj);
	}

	@Override
	public void eliminarAutor(int idAutor) {
		repoA.deleteById(idAutor);
		
	}

}
