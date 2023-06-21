package com.cibertec.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.entity.Sala;

public interface SalaService {

	
	public Sala InsertaSala(Sala obj);
	public Sala InsertaActualizaSala(Sala obj);
	public abstract void EliminaSala(int idSala);
	public List<Sala> ListaSala();
	public List<Sala> listaSalaporNumero(String Numero);
	Optional<Sala> findByIdSala(int idSala);
	
	public List<Sala> listaSalapornumeroAlumnosSede(String numero, int numAlumnos, int idSede, int estado);

}
