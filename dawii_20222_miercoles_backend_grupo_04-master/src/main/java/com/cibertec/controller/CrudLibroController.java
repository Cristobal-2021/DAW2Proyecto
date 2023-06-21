package com.cibertec.controller;

import java.util.Date;
import java.util.HashMap;
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

@RestController
@RequestMapping("/url/crudLibro")
@CrossOrigin(origins = "http://localhost:4200")
public class CrudLibroController {
	
	@Autowired
	LibroService service;
	
	
	@SuppressWarnings("unused")
	@PostMapping
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> insertarLibro(@RequestBody Libro obj){
		HashMap<String, Object> salida = new HashMap<String, Object>();

		try {
			obj.setEstado(1);
			obj.setFechaRegistro(new Date());
			if(obj != null) {
				List<Libro> lista = service.listaLibroserie(obj.getSerie());
				if(lista.isEmpty()) {
					Libro objSalida = service.InsertarActualizarLibro(obj);
					if(objSalida != null) {
						salida.put("mensaje", "Se registro el alumno de ID ==> "+obj.getIdLibro());
					}
					else {
						salida.put("error", "error en el registro");
					}
				}
				else {
					salida.put("error", "el numero de serie ya se encuentra registrado");
				}
			}
			else {
				salida.put("error", "el objeto que envio es nulo");
			}
		}
		catch(Exception ex) {
			salida.put("error", "Error en el registro");
			ex.printStackTrace();
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
	
	
	
	
	@PutMapping("/actualizaLibro")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> actualizaLibro(@RequestBody Libro obj){
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			if(obj != null) {
				Optional<Libro> objSalida = service.buscarLibroPorId(obj.getIdLibro());
				if(objSalida.isPresent()) {
				List<Libro> listaAlumnoDni = service.listaLibroSerieDiferenteDelMismoID(obj.getSerie(), obj.getIdLibro());
				if(listaAlumnoDni.isEmpty()) {
					Libro objSaldia =  service.actualizaLibro(obj);
					if(objSaldia != null) {
						salida.put("mensaje", "Se actualizo correctamente");
					}
					else {
						salida.put("error","Error en la actualizacion");
					}
				}
				else {
					salida.put("error", "La Serie ingresado ya se encuentra registrado");
				}	
			  }
			else {
				salida.put("error", "El id al que intenta acceder no existe");

			 }

			}
			else {
				salida.put("error", "El objeto ingresado es nulo");
			}

		}
		catch(Exception ex){
			salida.put("error", "Error en la actualizacion");
			ex.printStackTrace();
		}

		return ResponseEntity.ok(salida);

	}

	@GetMapping("/busca/{idLibro}")
	@ResponseBody
	public ResponseEntity<Libro> obtenerLibro(@PathVariable("idLibro") int idLibro){
		Libro obj  = new Libro();
		try {
		 obj = service.buscarLibroPorIdLibro(idLibro);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return ResponseEntity.ok(obj);

	}


	@DeleteMapping("/eliminaLibro/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaLibro(@PathVariable("id") int idLibro) {
		Map<String, Object> salida = new HashMap<>();
		try {
			service.eliminaLibro(idLibro);
			salida.put("mensaje", "Se elimino correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("error", "Error en la eliminacion");
		}
		return ResponseEntity.ok(salida);
	}

}
