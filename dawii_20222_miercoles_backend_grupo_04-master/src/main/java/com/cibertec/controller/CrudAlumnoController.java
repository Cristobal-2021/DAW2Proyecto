package com.cibertec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.entity.Alumno;
import com.cibertec.service.AlumnoService;

@RestController
@RequestMapping("/url/crudAlumno")
@CrossOrigin(origins = "http://localhost:4200")
public class CrudAlumnoController {
		
	@Autowired
	AlumnoService service;
	
	@SuppressWarnings("unused")
	@PostMapping
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> insertarAlumno(@RequestBody Alumno obj){
		HashMap<String, Object> salida = new HashMap<String, Object>();
		
		try {
			obj.setEstado(1);
			obj.setFechaRegistro(new Date());
			if(obj != null) {
				List<Alumno> lista = service.listaAlumnoDni(obj.getDni());
				if(lista.isEmpty()) {
					Alumno objSalida = service.insertarAlumno(obj);
					if(objSalida != null) {
						salida.put("mensaje", "Se registro el alumno de ID ==> "+obj.getIdAlumno());
					}
					else {
						salida.put("error", "error en el registro");
					}
				}
				else {
					salida.put("error", "el numero de dni ya se encuentra registrado");
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
	
	@GetMapping("/listaAlumnoConParametros")
	@ResponseBody
	public ResponseEntity<List<Alumno>> listaAlumnoDniEstadoIdPais(
			@RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(name = "apellido", required = false, defaultValue = "") String apellido,
			@RequestParam(name = "dni", required = false, defaultValue = "") String dni,
			@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
			@RequestParam(name = "estado", required = true, defaultValue = "1") int estado) {
		List<Alumno> lista = new ArrayList<Alumno>();
		try {
			lista = service.listaAlumnoDniEstadoIdPais("%"+nombre+"%","%"+apellido+"%", dni, estado,idPais);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PutMapping("/actualizaAlumno")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> actualizaAlumno(@RequestBody Alumno obj){
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			if(obj != null) {
				Optional<Alumno> objSalida = service.buscarAlumnoPorId(obj.getIdAlumno());
				if(objSalida.isPresent()) {
				List<Alumno> listaAlumnoDni = service.listaAlumnoDniDiferenteDelMismoID(obj.getDni(), obj.getIdAlumno());
				if(listaAlumnoDni.isEmpty()) {
					Alumno objSaldia =  service.actualizaAlumno(obj);
					if(objSaldia != null) {
						salida.put("mensaje", "Se actualizo correctamente");
					}
					else {
						salida.put("error","Error en la actualizacion");
					}
				}
				else {
					salida.put("error", "El dni ingresado ya se encuentra registrado");
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
	
	@GetMapping("/busca/{idAlumno}")
	@ResponseBody
	public ResponseEntity<Alumno> obtenerAlummno(@PathVariable("idAlumno") int idAlumno){
		Alumno obj  = new Alumno();
		try {
		 obj = service.buscarAlumnoPorIdAlumno(idAlumno);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return ResponseEntity.ok(obj);
		
	}
	
	
	@DeleteMapping("/eliminaAlumno/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaAlumno(@PathVariable("id") int idAlumno) {
		Map<String, Object> salida = new HashMap<>();
		try {
			service.eliminaAlumno(idAlumno);
			salida.put("mensaje", "Se elimino correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("error", "Error en la eliminacion");
		}
		return ResponseEntity.ok(salida);
	}
	
}
