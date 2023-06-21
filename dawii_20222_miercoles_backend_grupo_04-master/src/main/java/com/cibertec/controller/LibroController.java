package com.cibertec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * @author LUIS CUYA
 */
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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

import com.cibertec.entity.Libro;
import com.cibertec.service.LibroService;
import com.cibertec.util.AppSettings;

@RestController
@RequestMapping("/url/libro")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class LibroController {
	
	@Autowired LibroService service;
	
	
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> InsertarLibro(@RequestBody Libro objLibro){
		Map<String, Object> salida= new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);
			
		objLibro.setFechaRegistro(new Date());
		objLibro.setEstado(1);
		
		Libro objSalida= service.InsertarActualizarLibro(objLibro);
		if (objSalida==null) {
			lstMensajes.add("Error en el Registro");
			
		}else {
			lstMensajes.add("Se registró el Libro de ID ==> " + objSalida.getIdLibro());
		}
		
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping
	@ResponseBody
	public ResponseEntity<?> ActualizarLibro(@RequestBody Libro objLibro){
		Map<String, Object> salida= new HashMap<>();
		Optional<Libro> optLibro=service.BuscaLibro(objLibro.getIdLibro());
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);
		
		if (optLibro.isPresent()) {
			Libro objSalida= service.InsertarActualizarLibro(objLibro);
			if (objSalida==null) {
				lstMensajes.add( "Error en Actualizar");
				
			}else {
				lstMensajes.add( "Se Actualizo el Libro de ID => " + objSalida.getIdLibro());
			}
		}else {
			lstMensajes.add("No existe el Libro de Id => " + objLibro.getIdLibro());
		}
		
		return ResponseEntity.ok(salida);
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> EliminarLibro(@PathVariable("id") int id) {
		Map<String, Object> salida = new HashMap<>();
		List<String> lstMensajes = new ArrayList<>();
		salida.put("errores", lstMensajes);
		try {
			Optional<Libro> optLibro = service.BuscaLibro(id);
			if (optLibro.isPresent()) {
				service.EliminarLibro(id);
				lstMensajes.add( "Eliminacion  Exitosa");

			} else {
				lstMensajes.add( "el Id no Existe => " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			lstMensajes.add("Error en la eliminacion" + e.getMessage());

		}

		return ResponseEntity.ok(salida);
	}
	
	
	
	@GetMapping("/listaLibrosParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaLibroPorTitu_ser_cate_est(
			@RequestParam(name = "titulo", required = false, defaultValue = "") String titulo,
			@RequestParam(name = "serie", required = false, defaultValue = "") String serie,
			@RequestParam(name = "idCategoria", required = false, defaultValue = "-1") int idCategoria,
			@RequestParam(name = "estado", required = true, defaultValue = "1") int estado) {
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Libro> lista = service.listaLibroconParametros("%"+titulo+"%", serie, estado, idCategoria);
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar");
			}else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " elementos para mostrar");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en el registro");
		}
		return ResponseEntity.ok(salida);
	}
	
	

}
