import { Component, OnInit } from '@angular/core';
import { Alumno } from 'src/app/models/alumno.model';
import { Pais } from 'src/app/models/pais.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2'


@Component({
  selector: 'app-consulta-alumno',
  templateUrl: './consulta-alumno.component.html',
  styleUrls: ['./consulta-alumno.component.css']
})
export class ConsultaAlumnoComponent implements OnInit {


  //Ng model
  nombre:string="";
  apellido:string="";
  dni:string="";
  idPais:number = -1;
  estado:boolean = true;

  //Pais
  lstPaises: Pais[] = [];

  //lista
  lstAlumnos: Alumno[] = [];

  constructor(private utilService: UtilService, private alumnoService: AlumnoService) {
    this.utilService.listaPais().subscribe(
      x => this.lstPaises = x
    );
   }

   consultaAlumno(){
    this.alumnoService.consultaAlumno(this.nombre, this.apellido, this.dni, this.idPais, this.estado?1:0).subscribe(
      x => {
        Swal.fire('Mensaje', x.mensaje,'info');
        this.lstAlumnos = x.lista
      }  
    );
   }

  ngOnInit(): void {
  }

}
