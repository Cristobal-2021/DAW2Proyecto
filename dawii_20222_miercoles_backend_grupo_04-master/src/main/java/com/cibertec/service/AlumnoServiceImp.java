package com.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.entity.Alumno;
import com.cibertec.repository.AlumnoRepository;

@Service
public class AlumnoServiceImp implements AlumnoService {

	@Autowired
	private AlumnoRepository Repository;

	@Override
	public List<Alumno> listaTodos() {
		return Repository.findAll();

	}

	@Override
	public Alumno insertarAlumno(Alumno obj) {
		// TODO Auto-generated method stub
		return Repository.save(obj);
	}

	@Override
	public List<Alumno> listaAlumnoDni(String dni) {
		// TODO Auto-generated method stub
		return Repository.listaAlumnoDni(dni);
	}

	@Override
	public List<Alumno> listaAlumnoDniEstadoIdPais(String nombre,String apellido, String dni, int estado, int idPais) {
		// TODO Auto-generated method stub
		return Repository.listaAlumnoDniEstadoIdPais(nombre,apellido, dni, estado, idPais);
	}

	@Override
	public Alumno actualizaAlumno(Alumno obj) {
		// TODO Auto-generated method stub
		return Repository.save(obj);
	}

	@Override
	public void eliminaAlumno(int idAlumno) {
		// TODO Auto-generated method stub
		Repository.deleteById(idAlumno);
	}

	@Override
	public List<Alumno> listaAlumnoDniDiferenteDelMismoID(String dni, int id) {
		// TODO Auto-generated method stub
		return Repository.listaAlumnoDniDiferenteDelMismoID(dni, id);
	}

	@Override
	public Optional<Alumno> buscarAlumnoPorId(int id) {
		// TODO Auto-generated method stub
		return Repository.findById(id);
	}

	@Override
	public Alumno buscarAlumnoPorIdAlumno(int idAlumno) {
		// TODO Auto-generated method stub
		return Repository.findByIdAlumno(idAlumno);
	}

}
