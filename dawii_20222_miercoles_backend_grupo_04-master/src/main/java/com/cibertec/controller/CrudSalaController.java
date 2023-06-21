package com.cibertec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.entity.Sala;
import com.cibertec.service.SalaService;



@RestController
@RequestMapping("/rest/crudSala")
@CrossOrigin(origins = "http://localhost:4200")
public class CrudSalaController {
	
	@Autowired
	private SalaService salaservice;
	
	@GetMapping("/listaSalaporNumero/{numero}")
	@ResponseBody
	public ResponseEntity<List<Sala>> listaModalidad(@PathVariable("numero") String numero) {
		List<Sala> lista  = null;
		try {
			if (numero.equals("A")) {
				lista = salaservice.listaSalaporNumero("%");
			}else {
				lista = salaservice.listaSalaporNumero("%" + numero + "%");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/registraSala")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaSala(@RequestBody Sala obj) {
		
			Map<String, Object> salida = new HashMap<>();
		try {
			obj.setIdSala(0);
			obj.setFechaRegistro(new Date());
			obj.setEstado(1);
			List<Sala> lstsala = salaservice.listaSalaporNumero(obj.getNumero());
			if (lstsala.isEmpty()) {
				Sala objSalida = salaservice.InsertaActualizaSala(obj);
				if (objSalida == null) {
					salida.put("mensaje", "Error en el registro");
				}else {
					
					salida.put("mensaje", "Se registró Sala con ID ==> " + obj.getIdSala());
					
				}
				
			}else {
				salida.put("mensaje", "Numero de Sala ya se encuentra registrado");
			}
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en el registro");
			}
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping("/actualizaSala")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaSala(@RequestBody Sala obj) {
		
			Map<String, Object> salida = new HashMap<>();

		try {
			Optional<Sala> lstsala = salaservice.findByIdSala(obj.getIdSala());
			if (lstsala.isPresent()) {
				Sala objSalida = salaservice.InsertaActualizaSala(obj);
				if (objSalida == null) {
					salida.put("mensaje", "Error en la actualizacion");
				}else {
					
					salida.put("mensaje", "Se actualizo Sala con ID ==> " + objSalida.getIdSala());
				}
			}else {
				salida.put("mensaje", "Sala no existe");
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en la actualizacion");
			}
		return ResponseEntity.ok(salida);
	}
	
	@DeleteMapping("/eliminaSala/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaSala(@PathVariable("id") int idSala) {
		Map<String, Object> salida = new HashMap<>();
		
		try {
			salaservice.EliminaSala(idSala);
			salida.put("mensaje", "Se elimino Sala");
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en la eliminacion");
		}
		return ResponseEntity.ok(salida);
	}
	
	

}
