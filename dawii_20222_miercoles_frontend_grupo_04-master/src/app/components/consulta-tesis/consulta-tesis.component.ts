import { Component, OnInit } from '@angular/core';
import { Alumno } from 'src/app/models/alumno.model';
import { Tesis } from 'src/app/models/tesis.model';
import { TesisService } from 'src/app/services/tesis.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-consulta-tesis',
  templateUrl: './consulta-tesis.component.html',
  styleUrls: ['./consulta-tesis.component.css']
})
export class ConsultaTesisComponent implements OnInit {

  //Ng model
  titulo:string="";
  dni:string="";
  //selAlumno: string = "-1";
  selAlumno:number = -1;
  estado:boolean = true;
  tema:string="";

  //Alumno
  //alumnosS: string[]  = [];
  alumnos: Alumno[] = [];

  //Grila
  tesis: Tesis[] = [];


  constructor(private utilService: UtilService, private tesisService: TesisService) {
    this.utilService.listaAlumno().subscribe(
      x => this.alumnos = x
    );
  }

  consultaTesis(){
    this.tesisService.listaTesisParametros(this.titulo, this.estado? 1:0, this.selAlumno, this.tema).subscribe(
      x => {
        Swal.fire('Mensaje', x.mensaje, 'info');
        this.tesis = x.lista
      }
    );
  }

  ngOnInit(): void {
  }

}
