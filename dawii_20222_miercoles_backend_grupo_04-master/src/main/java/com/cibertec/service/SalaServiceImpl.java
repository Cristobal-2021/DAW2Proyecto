package com.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.entity.Sala;
import com.cibertec.repository.SalaRepository;
@Service
public class SalaServiceImpl implements SalaService{

	@Autowired
	private SalaRepository salrepo;
	
	@Override
	public Sala InsertaActualizaSala(Sala obj) {
		return salrepo.save(obj);
	}

	@Override
	public List<Sala> listaSalaporNumero(String Numero) {
		return salrepo.listaSalaporNumero(Numero);
	}

	@Override
	public List<Sala> listaSalapornumeroAlumnosSede(String numero, int numAlumnos, int idSede, int estado) {
		return salrepo.listaSalapornumeroAlumnosSede(numero, numAlumnos, idSede, estado);
	}

	@Override
	public void EliminaSala(int idSala) {
		 salrepo.deleteById(idSala);
		
	}

	@Override
	public List<Sala> ListaSala() {
		return salrepo.findAll();
	}

	@Override
	public Optional<Sala> findByIdSala(int idSala) {
		return salrepo.findByIdSala(idSala);
	}

	@Override
	public Sala InsertaSala(Sala obj) {
		return salrepo.save(obj);
	}



}
