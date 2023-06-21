import { Component, OnInit } from '@angular/core';
import { Alumno } from 'src/app/models/alumno.model';
import { Pais } from 'src/app/models/pais.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-agregar-alumno',
  templateUrl: './agregar-alumno.component.html',
  styleUrls: ['./agregar-alumno.component.css']
})
export class AgregarAlumnoComponent implements OnInit {


  lstPaises: Pais[] = [];
  alumno: Alumno = {
    pais:{
        idPais: -1,
    }
  }

  constructor(private utilService: UtilService, private alumnoService: AlumnoService) { 
    this.utilService.listaPais().subscribe(
      x => this.lstPaises = x
    );
  }
  
  insertaAlumno(){
    this.alumnoService.registroAlumno(this.alumno).subscribe(
      x => Swal.fire({icon: 'info',title: 'Resultado del Registro',text: x.mensaje})
    )
  }

  ngOnInit(): void {
  }

}
