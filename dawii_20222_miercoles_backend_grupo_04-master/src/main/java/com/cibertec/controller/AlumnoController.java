package com.cibertec.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.entity.Alumno;
import com.cibertec.service.AlumnoService;
import com.cibertec.util.AppSettings;


@RestController
@RequestMapping("/url/alumno")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	
	@SuppressWarnings("unused")
	@PostMapping
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> insertarAlumno(@RequestBody Alumno obj){
		HashMap<String, Object> salida = new HashMap<String, Object>();
		
		try {
			obj.setEstado(1);
			obj.setFechaRegistro(new Date());
			if(obj != null) {
				List<Alumno> lista = alumnoService.listaAlumnoDni(obj.getDni());
				if(lista.isEmpty()) {
					Alumno objSalida = alumnoService.insertarAlumno(obj);
					if(objSalida != null) {
						salida.put("mensaje", "Se registro el alumno de ID ==> "+obj.getIdAlumno());
					}
					else {
						salida.put("mensaje", "error en el registro");
					}
				}
				else {
					salida.put("mensaje", "el numero de dni ya se encuentra registrado");
				}
			}
			else {
				salida.put("mensaje", "el objeto que envio es nulo");
			}
		}
		catch(Exception ex) {
			salida.put("mensaje", "Error en el registro");
			ex.printStackTrace();
		}
		return ResponseEntity.ok(salida);
		
	}
	
	@GetMapping("/listaAlumnoConParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaAlumnoDniEstadoIdPais(
			@RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(name = "apellido", required = false, defaultValue = "") String apellido,
			@RequestParam(name = "dni", required = false, defaultValue = "") String dni,
			@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
			@RequestParam(name = "estado", required = true, defaultValue = "1") int estado) {
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Alumno> lista = alumnoService.listaAlumnoDniEstadoIdPais("%"+nombre+"%","%"+apellido+"%", dni, estado,idPais);
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar");
			}else {
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
