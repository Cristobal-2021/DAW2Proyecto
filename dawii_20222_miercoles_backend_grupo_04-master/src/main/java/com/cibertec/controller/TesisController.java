package com.cibertec.controller;

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

import com.cibertec.entity.Tesis;
import com.cibertec.service.TesisService;
import com.cibertec.util.AppSettings;

@RestController
@RequestMapping("/url/tesis")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TesisController {

	@Autowired
	private TesisService tesisService;
	
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Tesis>> listaModalidad(){
		List<Tesis> lista = tesisService.listaTesis();
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	@ResponseBody
	public  ResponseEntity<?> insertaTesis(@Valid @RequestBody Tesis obj, Errors errors){
		Map<String, Object> salida = new HashMap<>();
		String lstMensajes = "";
		
		salida.put("mensaje", lstMensajes);
		
		List<ObjectError> lstErrors = errors.getAllErrors();
		
		for (ObjectError objectError : lstErrors) {
			objectError.getDefaultMessage();
			lstMensajes = objectError.getDefaultMessage();
		}
		if (lstMensajes.equals("")) {
			return ResponseEntity.ok(salida);
		}
		
		try {
			obj.setIdTesis(3);
			obj.setFechaRegistro(new Date());
			obj.setEstado(1);
			
			Tesis objSalida = tesisService.insertaActualizaTesis(obj);
			
			if (objSalida == null) {
				lstMensajes = "Error en el registro";
			} else {
				lstMensajes = "Se registró la tesis de ID ==> " + objSalida.getIdTesis();
			}
		} catch (Exception e) {
			lstMensajes = "Error en el registro";
			e.printStackTrace();
		}

		return ResponseEntity.ok(salida);
	}
	
	@GetMapping("/listaTesisConParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaTesisPorTituloEstadoAlumnoFecha_Creacion(
			@RequestParam(name = "titulo",		required = false,	defaultValue = "") String titulo,
			@RequestParam(name = "estado",		required = true, 	defaultValue = "1") int estado,
			@RequestParam(name = "idAlumno",	required = false,	defaultValue = "-1") int idAlumno,
			@RequestParam(name = "tema",	required = false,	defaultValue = "") String tema) {
		Map<String, Object> salida = new HashMap<>();
		
		try {
			List<Tesis> lista = tesisService.listaTesisPorTituloEstadoAlumnoTema("%" + titulo + "%", estado, idAlumno, "%" + tema + "%");
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar");
			}else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " elementos para mostrar");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "No se pudo realizar la consulta, hable con el administrador");
		}
		
		return ResponseEntity.ok(salida);
	}
	
}






















