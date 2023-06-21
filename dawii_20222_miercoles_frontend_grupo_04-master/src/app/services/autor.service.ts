import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../app.settings';
import { Autor } from '../models/autor.model';
 
const baseUrlAutor = AppSettings.API_ENDPOINT+ '/autor';
const baseUrlAutorCrud = AppSettings.API_ENDPOINT+ '/crudAutor';
 
 
 
@Injectable({
  providedIn: 'root'
})
export class AutorService {
 
  constructor(private http : HttpClient) { }
 
  consultaAutor(nombre:string, apellido:string, telefono:string, idGrado: number, estado: number): Observable<any>{
 
    const params = new HttpParams().set("nombre", nombre).set("apellido", apellido).set("telefono", telefono).set("idGrado", idGrado).set("estado", estado);  
 
    return this.http.get(baseUrlAutor + "/listaAutorConParametros", {params});
 
  }
 
 
  registroAutorCrud(data: Autor): Observable<any>{
    return this.http.post(baseUrlAutorCrud+"/registraAutor", data)
  }
 
  actualizaAutorCrud(data: Autor): Observable<any>{
    return this.http.post(baseUrlAutorCrud+"/actualizaAutor", data)
  }
 
  eliminaAutorCrud(idAlumno: number): Observable<any>{
    return this.http.delete(baseUrlAutorCrud + "/eliminaAutor/"+idAlumno)
  }
 
 
}
 


