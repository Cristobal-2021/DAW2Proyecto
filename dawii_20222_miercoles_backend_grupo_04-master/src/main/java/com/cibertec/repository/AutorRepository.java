package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
	@Query("select x from Autor x where (?1 is '' or x.nombres like ?1) and (?2 is '' or x.apellidos like ?2) and (?3 is '' or x.telefono = ?3) "

			+ "and x.estado = ?4 "

			+ "and (?5 is -1 or x.grado.idGrado = ?5)")
	public List<Autor> listaAutorNombreTelefonoEstadoGrado(String nombre, String apellido, String telefono,
			int estado, int idGrado);

}
