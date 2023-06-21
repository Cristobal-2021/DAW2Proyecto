package com.cibertec.service;

import java.util.List;

import com.cibertec.entity.Tesis;

public interface TesisService {

	public abstract List<Tesis> listaTesis();
	public abstract List<Tesis> listaTesisPorTituloEstadoAlumnoTema(String titulo, int estado, int idAlumno, String tema);
	
	//CRUD
	public abstract Tesis insertaActualizaTesis(Tesis obj);
	public abstract List<Tesis> listaTesisPorTituloLike(String titulo);
	public abstract void eliminaTesis(int idTesis);
	
}
