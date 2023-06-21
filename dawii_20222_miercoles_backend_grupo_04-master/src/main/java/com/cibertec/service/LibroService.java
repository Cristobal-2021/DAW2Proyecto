package com.cibertec.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.entity.Libro;

public interface LibroService {
	
	public abstract List<Libro> ListarLibro();
	
	public abstract void EliminarLibro(int idLibro);
	
	public abstract Optional<Libro> BuscaLibro(int idLibro);
	
	public abstract Libro InsertarActualizarLibro(Libro obj); 
	
	public abstract List<Libro> listaLibroconParametros(String titulo, String serie,  int estado, int idCategoria);
	
	
	
	/*-------------------------Crud-----------------------------------------*/
	
	public abstract Libro actualizaLibro(Libro obj);

	public abstract void eliminaLibro(int idLibro);

	public abstract List<Libro> listaLibroSerieDiferenteDelMismoID(String serie, int id);

	public Optional<Libro> buscarLibroPorId(int id);

	public Libro buscarLibroPorIdLibro(int idLibro);
	
	public abstract List<Libro> listaLibroserie(String serie);
}
