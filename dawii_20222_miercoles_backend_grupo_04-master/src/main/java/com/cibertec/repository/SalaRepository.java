package com.cibertec.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.entity.Sala;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
	
	@Query("select x from Sala x where (x.numero like ?1) and (?2 is -1 or x.numAlumnos=?2) and (?3 is -1 or x.sede.idSede =?3) and (x.estado = ?4) ")
	public List<Sala> listaSalapornumeroAlumnosSede(String numero, int numAlumnos, int idSede, int estado);

	@Query("select x from Sala x where x.numero like ?1 ")
	public List<Sala> listaSalaporNumero(String numero);
	
	Optional<Sala> findByIdSala(int idSala);

}
