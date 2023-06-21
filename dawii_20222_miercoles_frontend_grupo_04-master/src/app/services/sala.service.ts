import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../app.settings';
import { Sala } from '../models/sala.model';

const baseUrlSala = AppSettings.API_ENDPOINT+ '/sala';
const baseUrlSalaCrud = 'http://localhost:8090/rest/crudSala';

@Injectable({
  providedIn: 'root'
})
export class SalaService {

  constructor(private http: HttpClient) { }

    registraSala(obj:Sala): Observable<any> {
      return this.http.post(baseUrlSala, obj);
    
    }
    listaSala(numero:string, numAlumnos:number, idSede: number, estado : number): Observable<any>{
        const params = new HttpParams().set("numero", numero).set("numAlumnos", numAlumnos).set("idSede", idSede).set("estado", estado);  
        return this.http.get(baseUrlSala + "/listaSalaConParametros", {params});
      }

      consultaPorNumeroCrud(filtro:string):Observable<Sala[]>{
        return  this.http.get<Sala[]>(baseUrlSalaCrud +"/listaSalaporNumero/"+filtro); 
    }  
  
    insertaCrud(obj:Sala):Observable<any>{
        return this.http.post(baseUrlSalaCrud + "/registraSala", obj);
    }
  
    actualizaCrud(obj:Sala):Observable<any>{
        return this.http.put(baseUrlSalaCrud + "/actualizaSala", obj);
    }
  
    eliminaCrud(idSala:number):Observable<any>{
        return this.http.delete(baseUrlSalaCrud + "/eliminaSala/"+ idSala);
    }
      
  }

