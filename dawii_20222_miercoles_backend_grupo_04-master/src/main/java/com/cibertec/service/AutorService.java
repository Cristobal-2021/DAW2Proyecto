package com.cibertec.service;

import java.util.List;

import com.cibertec.entity.Autor;

public interface AutorService {
	public List<Autor> listaAutorNombreTelefonoEstadoGrado(String nombre, String apellido, String telefono, int estado,
			int idGrado);
	
	public Autor insertarActualizarAutor(Autor obj);
	
	public void eliminarAutor(int idAutor);

	
}
