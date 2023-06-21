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

import com.cibertec.entity.Tesis;
import com.cibertec.service.TesisService;
import com.cibertec.util.AppSettings;

@RestController
@RequestMapping("/url/crudTesis")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class CrudTesisController {

	@Autowired
	private TesisService service;
	
	
	@GetMapping("/listaTesisPorTituloLike/{titulo}")
	@ResponseBody
	public ResponseEntity<List<Tesis>> listaTesisPorTituloLike(@PathVariable("titulo") String tit) {
		List<Tesis> lista  = null;
		try {
			if (tit.equals("todos")) {
				lista = service.listaTesisPorTituloLike("%");
			}else {
				lista = service.listaTesisPorTituloLike("%" + tit + "%");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/registraTesis")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaTesis(@RequestBody Tesis obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			obj.setIdTesis(0);
			obj.setFechaRegistro(new Date());
			obj.setEstado(1);
			Tesis objSalida =  service.insertaActualizaTesis(obj);
			if (objSalida == null) {
				salida.put("mensaje", "No se registró la tesis, consulte con el administrador.");
			} else {
				salida.put("mensaje", "Se registró la tesis correctamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en el registro, consulte con el administrador.");
		}
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping("/actualizaTesis")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaTesis(@RequestBody Tesis obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			Tesis objSalida =  service.insertaActualizaTesis(obj);
			if (objSalida == null) {
				salida.put("mensaje", "No se actualizó la tesis, consulte con el administrador.");
			} else {
				salida.put("mensaje", "Se actualizó la tesis correctamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en la acutalización, consulte con el administrador.");
		}
		return ResponseEntity.ok(salida);
	}
	
	@DeleteMapping("/eliminaTesis/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaTesis(@PathVariable("id") int idTesis) {
		Map<String, Object> salida = new HashMap<>();
		try {
			service.eliminaTesis(idTesis);
			salida.put("mensaje", "Se eliminó correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "No se eliminó, ya que el registro esta relacionado.");
		}
		return ResponseEntity.ok(salida);
	}
	
}
