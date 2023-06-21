import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../app.settings';
import { Alumno } from '../models/alumno.model';

const baseUrlAlumno = AppSettings.API_ENDPOINT+ '/alumno';
const baseUrlCrudAlumno = AppSettings.API_ENDPOINT+ '/crudAlumno';


@Injectable({
  providedIn: 'root'
})
export class AlumnoService {

  constructor(private http : HttpClient) { }

  registroAlumno(data: Alumno): Observable<any>{
    return this.http.post(baseUrlAlumno, data)
  }

  registroAlumnoCrud(data: Alumno): Observable<any>{
    return this.http.post(baseUrlCrudAlumno, data)
  }


  actualizaAlumnoCrud(data: Alumno): Observable<any>{
    return this.http.put(baseUrlCrudAlumno + "/actualizaAlumno", data)
  }

  eliminaAlumnoCrud(idAlumno: number): Observable<any>{
    return this.http.delete(baseUrlCrudAlumno + "/eliminaAlumno/"+idAlumno)
  }

  consultaAlumno(nombre:string, apellido:string, dni:string, idPais: number, estado: number): Observable<any>{

    const params = new HttpParams().set("nombre", nombre).set("apellido", apellido).set("dni", dni).set("idPais", idPais).set("estado", estado);  

    return this.http.get(baseUrlAlumno + "/listaAlumnoConParametros", {params});

  }

  consultaAlumnoCrud(nombre:string, apellido:string, dni:string, idPais: number, estado: number): Observable<any>{

    const params = new HttpParams().set("nombre", nombre).set("apellido", apellido).set("dni", dni).set("idPais", idPais).set("estado", estado);  

    return this.http.get(baseUrlCrudAlumno + "/listaAlumnoConParametros", {params});

  }

  obtenerAlumnoCrud(idAlumno:number): Observable<any>{
    return this.http.get(baseUrlCrudAlumno+"/busca/"+idAlumno);
  }

}
