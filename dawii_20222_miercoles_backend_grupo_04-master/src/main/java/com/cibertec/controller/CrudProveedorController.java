package com.cibertec.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.entity.Proveedor;
import com.cibertec.service.ProveedorService;
import com.cibertec.util.AppSettings;

@RestController
@RequestMapping("/url/crudProveedor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class CrudProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;
	
	@GetMapping("/listaProveedorPorRazonLike/{raz}")
	@ResponseBody
	public ResponseEntity<List<Proveedor>> listaProveedorPorRazonLike(@PathVariable("raz") String raz){
		List<Proveedor> lista = null;
		try {
			if (raz.equals("todos")) {
				lista = proveedorService.listaProveedorPorRazonLike("%");
			}else {
				lista = proveedorService.listaProveedorPorRazonLike("%" + raz + "%");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/registraProveedor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaProveedor(@RequestBody Proveedor obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			obj.setFechaRegistro(new Date());
			obj.setEstado(1);
			Proveedor objSalida =  proveedorService.insertaActualizaProveedor(obj);
			if (objSalida == null) {
				salida.put("mensaje", "Error al registrar.");
			} else {
				salida.put("mensaje", "Se registró correctamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error al registrar. Consulte al administrador.");
		}
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping("/actualizaProveedor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaProveedor(@RequestBody Proveedor obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			Proveedor objSalida =  proveedorService.insertaActualizaProveedor(obj);
			if (objSalida == null) {
				salida.put("mensaje", "Error al actualizar");
			} else {
				salida.put("mensaje", "Se actualizó correctamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error al actualizar. Consulte al administrador.");
		}
		return ResponseEntity.ok(salida);
	}
	
	
	@DeleteMapping("/eliminaProveedor/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaProveedor(@PathVariable("id") int idProveedor) {
		Map<String, Object> salida = new HashMap<>();
		try {
			proveedorService.eliminaProveedor(idProveedor);
			salida.put("mensaje", "Se eliminó correctamente al proveedor.");
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Falló al eliminar el proveedor.");
		}
		return ResponseEntity.ok(salida);
	}
	
}
