import { Component, OnInit } from '@angular/core';
import { SalaService } from 'src/app/services/sala.service';
import { Sala } from 'src/app/models/sala.model';
import Swal from 'sweetalert2';
import { UtilService } from 'src/app/services/util.service';
import { Sede } from 'src/app/models/sede.model';


@Component({
  selector: 'app-consulta-sala',
  templateUrl: './consulta-sala.component.html',
  styleUrls: ['./consulta-sala.component.css']
})
export class ConsultaSalaComponent implements OnInit {

  numero:string= "";
  numAlumnos:number=-1;
  selSede:number=-1;
  estado:boolean= true;
  lstsede: Sede [] = [];

  //Grilla
  salas:Sala[] = [];

  constructor( private sedeService:UtilService, private salaService:SalaService) {
    this.sedeService.listaSede().subscribe(
      x => this.lstsede = x
    );
   }

   consultaSala(){
    this.salaService.listaSala(this.numero, this.numAlumnos, this.selSede, this.estado?1:0).subscribe(
        x =>{
            this.salas = x.lista;
            Swal.fire('Mensaje', x.mensaje,'info');
        } 
    );
}
  ngOnInit(): void {
  }
 
}
