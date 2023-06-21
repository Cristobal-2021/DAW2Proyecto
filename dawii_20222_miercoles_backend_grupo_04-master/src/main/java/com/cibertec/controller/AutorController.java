package com.cibertec.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.entity.Autor;
import com.cibertec.service.AutorService;
import com.cibertec.util.AppSettings;

@RestController
@RequestMapping("/url/autor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class AutorController {
	@Autowired
	AutorService serviceAutor;

	@GetMapping("/listaAutorConParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaAlumnoDniEstadoIdPais(
			@RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(name = "apellido", required = false, defaultValue = "") String apellido,
			@RequestParam(name = "telefono", required = false, defaultValue = "") String telefono,
			@RequestParam(name = "idGrado", required = false, defaultValue = "-1") int idGrado,
			@RequestParam(name = "estado", required = true, defaultValue = "1") int estado) {
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Autor> lista = serviceAutor.listaAutorNombreTelefonoEstadoGrado("%" + nombre + "%",
					"%" + apellido + "%", telefono, estado, idGrado);
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar");
			} else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " elementos para mostrar");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error al listar");
		}
		return ResponseEntity.ok(salida);
	}

}
