package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.entity.Tesis;

public interface TesisRepository extends JpaRepository<Tesis, Integer> {

	@Query("select x from Tesis x where (?1 is '' or x.titulo like ?1) and x.estado = ?2 "
			+ "and (?3 is -1 or x.alumno.idAlumno = ?3) and (?4 is '' or x.tema like ?4)")
	public List<Tesis> listaTesisPorTituloEstadoAlumnoTema(String titulo, int estado, int idAlumno, String tema);  
	
	@Query("select x from Tesis x where x.titulo like ?1")
	public List<Tesis> listaTesisPorTituloLike(String titulo);
	
}
