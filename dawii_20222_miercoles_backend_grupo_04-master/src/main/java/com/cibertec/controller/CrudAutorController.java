package com.cibertec.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.entity.Autor;
import com.cibertec.service.AutorService;


@RestController
@RequestMapping("/url/crudAutor")
@CrossOrigin(origins = "http://localhost:4200")
public class CrudAutorController {
	
	@Autowired
	AutorService serviceAutor;
	
	@PostMapping("/registraAutor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaAutor(@RequestBody Autor obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			obj.setIdAutor(0);
			obj.setFechaRegistro(new Date());
			obj.setEstado(1);
			Autor objSalida =  serviceAutor.insertarActualizarAutor(obj);
			if (objSalida == null) {
				salida.put("error", "Error en el registro de autor");
			} else {
				salida.put("mensaje", "Exito en el registro");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("error", "Error en el registro de autor");
		}
		return ResponseEntity.ok(salida);
	}
	
	@PostMapping("/actualizaAutor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizarAutor(@RequestBody Autor obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			Autor objSalida =  serviceAutor.insertarActualizarAutor(obj);
			if (objSalida == null) {
				salida.put("error", "Error en la actualizacion del autor");
			} else {
				salida.put("mensaje", "Exito en la actualizacion");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("error", "Error en la actualizacion del autor");
		}
		return ResponseEntity.ok(salida);
	}
	
	@DeleteMapping("/eliminaAutor/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaAutor(@PathVariable("id") int idAutor) {
		Map<String, Object> salida = new HashMap<>();
		try {
			serviceAutor.eliminarAutor(idAutor);
			salida.put("mensaje", "Se elimino correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("error", "Error en la eliminacion");
		}
		return ResponseEntity.ok(salida);
	}
}



