package com.cibertec.service;

import java.util.List;

import com.cibertec.entity.Prueba;

public interface PruebaService {

	public abstract List<Prueba> listaPrueba();
	public abstract Prueba insertaActualizaPrueba(Prueba obj);

}
