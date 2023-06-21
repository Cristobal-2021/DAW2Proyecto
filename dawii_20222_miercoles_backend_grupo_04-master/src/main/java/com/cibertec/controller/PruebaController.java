package com.cibertec.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.entity.Prueba;
import com.cibertec.service.PruebaService;
import com.cibertec.util.AppSettings;

@RestController
@RequestMapping("/url/prueba")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class PruebaController {

	@Autowired
	private PruebaService pruebaService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Prueba>> listaAlumno(){
		List<Prueba> lista = pruebaService.listaPrueba();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> inserta(@Valid @RequestBody Prueba obj, Errors errors){
		HashMap<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<String>();
		salida.put("errores", lstMensajes);
		
		List<ObjectError> lstErrors =  errors.getAllErrors();
		for (ObjectError objectError : lstErrors) {
			objectError.getDefaultMessage();
			lstMensajes.add(objectError.getDefaultMessage());
		}

		if (!CollectionUtils.isEmpty(lstMensajes)) {
			return ResponseEntity.ok(salida);
		}
		
		Prueba objSalida = pruebaService.insertaActualizaPrueba(obj);
		if (objSalida == null) {
			lstMensajes.add("Error en el registro");
		}else {
			lstMensajes.add("Se registró la prueba con el ID ==> " + objSalida.getIdPrueba());
		}
		return ResponseEntity.ok(salida);
	}
}
