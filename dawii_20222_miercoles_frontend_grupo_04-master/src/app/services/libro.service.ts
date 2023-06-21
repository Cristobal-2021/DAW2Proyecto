import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../app.settings';
import { Libro } from '../models/libro.model';

const baseUrlLibro = AppSettings.API_ENDPOINT+ '/libro';
const baseUrlCrudLibro = AppSettings.API_ENDPOINT+ '/crudLibro';

@Injectable({
  providedIn: 'root'
})
export class LibroService {

  constructor( private http:HttpClient) { }


  registraLibro(obj:Libro):Observable<any>{
    return this.http.post(baseUrlLibro,obj)
  }


  registroLibroCrud(data: Libro): Observable<any>{
    return this.http.post(baseUrlCrudLibro, data)
  }

  actualizaLibroCrud(data: Libro): Observable<any>{
    return this.http.put(baseUrlCrudLibro + "/actualizaLibro", data)
  }

  eliminaLibroCrud(idLibro: number): Observable<any>{
    return this.http.delete(baseUrlCrudLibro + "/eliminaLibro/"+idLibro)
  }




  consultaLibro(titulo:string, serie:string, idCategoria: number, estado: number): Observable<any>{

    const params = new HttpParams().set("titulo", titulo).set("serie", serie).set("idCategoria", idCategoria).set("estado", estado);  

    return this.http.get(baseUrlLibro + "/listaLibrosParametros", {params});
  }



  consultaLibroCrud(titulo:string, serie:string, idCategoria: number, estado: number): Observable<any>{

    const params = new HttpParams().set("titulo", titulo).set("serie", serie).set("idCategoria", idCategoria).set("estado", estado);  

    return this.http.get(baseUrlCrudLibro + "/listaLibrosParametros", {params});

  }

  obtenerLibroCrud(idLibro:number): Observable<any>{
    return this.http.get(baseUrlCrudLibro+"/busca/"+idLibro);
  }




}
