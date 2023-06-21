package com.cibertec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.entity.Proveedor;
import com.cibertec.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService{

	@Autowired
	private ProveedorRepository repoProveedor;
	
	@Override
	public Proveedor insertaActualizaProveedor(Proveedor obj) {		
		if(obj.getIdProveedor() == 0) {
			repoProveedor.insertaProveedor(obj.getRazonsocial(), obj.getRuc(), obj.getDireccion(), obj.getCelular(), 
					obj.getContacto(), obj.getEstado(), obj.getPais().getIdPais());
			return repoProveedor.buscaUltimoProveedor();			
		}else {
			repoProveedor.actualizaProveedor(obj.getRazonsocial(), obj.getRuc(), obj.getDireccion(), obj.getCelular(), 
					obj.getContacto(), obj.getEstado(), obj.getPais().getIdPais(), obj.getIdProveedor());
			return repoProveedor.buscaProveedorPorPk(obj.getIdProveedor());
		}		
	}
	
	@Override
	public List<Proveedor> listaProveedor() {
		return repoProveedor.findAll();	
	}

	@Override
	public List<Proveedor> listaProveedorPorRazonRucEstadoPais(String razonsocial, String ruc, int estado, int idPais) {
		return repoProveedor.listaProveedorPorRazonRucEstadoPais(razonsocial, ruc, estado, idPais);
	}

	@Override
	public List<Proveedor> listaProveedorPorRazonLike(String razonsocial) {
		return repoProveedor.listaPorRazonLike(razonsocial);
	}

	@Override
	public void eliminaProveedor(int idProveedor) {
		repoProveedor.eliminaProveedor(idProveedor);
		
	}
}
