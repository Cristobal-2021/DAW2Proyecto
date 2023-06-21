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

import com.cibertec.entity.Sala;
import com.cibertec.service.SalaService;
import com.cibertec.util.AppSettings;




@RestController
@RequestMapping("/url/sala")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class SalaController {

	@Autowired
	private SalaService salaservice;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Sala>> listaModalidad(){
		List<Sala> lista = salaservice.ListaSala();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping
	@ResponseBody
	public  ResponseEntity<?> insertaSala(@Valid @RequestBody Sala obj, Errors errors){
		Map<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);

		List<ObjectError> lstErrors =  errors.getAllErrors();
		for (ObjectError objectError : lstErrors) {
			objectError.getDefaultMessage();
			lstMensajes.add(objectError.getDefaultMessage());
		}
		if (!CollectionUtils.isEmpty(lstMensajes)) {
			return ResponseEntity.ok(salida);
		}
		obj.setFechaRegistro(new Date());
		obj.setEstado(1);
		Sala objSalida = salaservice.InsertaSala(obj);
		if (objSalida == null) {
			lstMensajes.add("Error en el registro");
		}else {
			lstMensajes.add("Se registró Sala con ID ==> " + objSalida.getIdSala());
		}
		
		return ResponseEntity.ok(salida);
	}
	
	@GetMapping("/listaSalaConParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaSalapornumeroAlumnosSede(
			@RequestParam(name = "numero", required = false, defaultValue = "") String numero,
			@RequestParam(name = "numAlumnos", required = false, defaultValue = "-1") int numAlumnos,
			@RequestParam(name = "idSede", required = false, defaultValue = "-1") int idSede,
			@RequestParam(name = "estado", required = true, defaultValue = "1") int estado) {
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Sala> lista = salaservice.listaSalapornumeroAlumnosSede("%"+numero+"%", numAlumnos, idSede, estado);
			
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar");
			}else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " elementos para mostrar");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "No se registró, consulte con el administrador.");
		}
		return ResponseEntity.ok(salida);
	}
	

}
