import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppSettings } from "../app.settings";
import { Tesis } from "../models/tesis.model";

const baseUrlTesis = AppSettings.API_ENDPOINT+ '/crudTesis';

@Injectable({
  providedIn: 'root'
})

export class crudTesisService {

  constructor(private http:HttpClient) {}

  consultaPorTitulo(filtro:string):Observable<Tesis[]>{
    return  this.http.get<Tesis[]>(baseUrlTesis + "/listaTesisPorTituloLike/" + filtro); 
  }  

  inserta(obj:Tesis):Observable<any>{
    return this.http.post(baseUrlTesis + "/registraTesis", obj);
  }

  actualiza(obj:Tesis):Observable<any>{
    return this.http.put(baseUrlTesis + "/actualizaTesis", obj);
  }

  elimina(idTesis:number):Observable<any>{
    return this.http.delete(baseUrlTesis + "/eliminaTesis/"+ idTesis);
  }
}
