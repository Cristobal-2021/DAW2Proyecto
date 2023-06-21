package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer>{
	
	@Query("Select a from Alumno a where dni = ?1")
	public List<Alumno> listaAlumnoDni(String dni);
	
	@Query("select x from Alumno x where (?1 is '' or x.nombres like ?1) and (?2 is '' or x.apellidos like ?2) and (?3 is '' or x.dni = ?3) "

			+ "and x.estado = ?4 "

			+ "and (?5 is -1 or x.pais.idPais = ?5)")
	public List<Alumno> listaAlumnoDniEstadoIdPais(String nombre, String apellido, String dni,  int estado, int idPais);
	
	@Query("Select a from Alumno a where dni = ?1 and id <> ?2")
	public List<Alumno> listaAlumnoDniDiferenteDelMismoID(String dni, int id);
	
	public Alumno findByIdAlumno(int idAlumno);
}
