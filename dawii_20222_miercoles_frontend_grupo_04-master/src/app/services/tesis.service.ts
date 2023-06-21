import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../app.settings';
import { Tesis } from '../models/tesis.model';

const baseUrlTesis = AppSettings.API_ENDPOINT+ '/tesis';

@Injectable({
  providedIn: 'root'
})
export class TesisService {

  constructor(private http:HttpClient) {

  }

  listaTesis():Observable<Tesis[]> {
    return this.http.get<Tesis[]>(baseUrlTesis);
  }

  listaTesisParametros(titulo: string, estado: number, idAlumno: number, tema: string):Observable<any> {
    const params = new HttpParams().set("titulo", titulo).set("estado", estado).set("idAlumno", idAlumno).set("tema", tema);
    return this.http.get(baseUrlTesis + "/listaTesisConParametros", {params});
  }

  registraTesis(obj:Tesis):Observable<any> {
    return this.http.post(baseUrlTesis, obj);
  }

}
