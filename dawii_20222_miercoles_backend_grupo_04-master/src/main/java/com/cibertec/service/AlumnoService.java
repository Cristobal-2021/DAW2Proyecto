package com.cibertec.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.entity.Alumno;

public interface AlumnoService {
	
	public abstract Alumno insertarAlumno(Alumno obj);
	
	public abstract List<Alumno> listaTodos();
	
	public abstract List<Alumno> listaAlumnoDni(String dni);

	public List<Alumno> listaAlumnoDniEstadoIdPais(String nombre, String apellido, String dni,  int estado, int idPais);
	
	public abstract Alumno actualizaAlumno(Alumno obj);
	
	public abstract void eliminaAlumno(int idAlumno);
	
	public abstract List<Alumno> listaAlumnoDniDiferenteDelMismoID(String dni, int id);

	public Optional<Alumno> buscarAlumnoPorId(int id);
	
	public Alumno buscarAlumnoPorIdAlumno(int idAlumno);

	
}
