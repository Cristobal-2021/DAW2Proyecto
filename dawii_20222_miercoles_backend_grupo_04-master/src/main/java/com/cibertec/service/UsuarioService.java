package com.cibertec.service;

import java.util.List;

import com.cibertec.entity.Opcion;
import com.cibertec.entity.Rol;
import com.cibertec.entity.Usuario;

public interface UsuarioService {

	public abstract Usuario login(Usuario bean);

	public abstract List<Opcion> traerEnlacesDeUsuario(int idUsuario);

	public abstract List<Rol> traerRolesDeUsuario(int idUsuario);

	public abstract Usuario buscaPorLogin(String login);
}
