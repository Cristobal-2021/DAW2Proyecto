package com.cibertec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.entity.Proveedor;
import com.cibertec.service.ProveedorService;
import com.cibertec.util.AppSettings;

@RestController
@RequestMapping("/url/proveedor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Proveedor>> listaProveedor(){
		List<Proveedor> lista = proveedorService.listaProveedor();
		return ResponseEntity.ok(lista);
	};
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> insertaProveedor(@RequestBody Proveedor obj){
		Map<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();	
		salida.put("errores", lstMensajes);		
		
		try {
			obj.setFechaRegistro(new Date());
			obj.setEstado(1);
			Proveedor objSalida = proveedorService.insertaActualizaProveedor(obj);
			if(objSalida == null) {
				lstMensajes.add("Error al registrar.");
			}else {
				lstMensajes.add("Se registró el proveedor con ID ==> " + objSalida.getIdProveedor());
			}
		} catch (Exception e) {
			lstMensajes.add("Ocurrió un error en el servidor");
			e.printStackTrace();
		}		
		
		return ResponseEntity.ok(salida);
	}
	
	@GetMapping("/listaProveedorConParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaProveedorPorRazonRucEstadoPais(
			@RequestParam(name = "razonsocial", required = false, defaultValue = "")String razonsocial,
			@RequestParam(name = "ruc", required = false, defaultValue = "")String ruc,
			@RequestParam(name = "estado", required = true, defaultValue = "1") int estado,
			@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais){
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Proveedor> lista = proveedorService.listaProveedorPorRazonRucEstadoPais("%"+razonsocial+"%", ruc, estado, idPais);
			if(CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar.");
			}else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " elementos para mostrar.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en la respuesta para el listado.");
		}
		return ResponseEntity.ok(salida);
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
