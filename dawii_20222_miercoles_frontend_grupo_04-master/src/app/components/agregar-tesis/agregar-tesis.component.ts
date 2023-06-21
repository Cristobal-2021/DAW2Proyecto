import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Alumno } from 'src/app/models/alumno.model';
import { Tesis } from 'src/app/models/tesis.model';
import { TesisService } from 'src/app/services/tesis.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-agregar-tesis',
  templateUrl: './agregar-tesis.component.html',
  styleUrls: ['./agregar-tesis.component.css']
})
export class AgregarTesisComponent implements OnInit {

  lstAlumno:Alumno[] = [];
  objTesis:Tesis = {
    alumno:{
      idAlumno:-1
    }
  };

  submitted = false;

  formsRegistra = new FormGroup({
    validaTitulo: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{20,200}')]),
    validaTema: new FormControl('', [Validators.required,Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{6,100}')]),
    validaFechaCreacion: new FormControl('', [Validators.required]),
    validaAlumno: new FormControl('', [Validators.min(0)])
  });

  constructor(private utilService:UtilService, private tesisService:TesisService) {
      this.utilService.listaAlumno().subscribe(
        x => this.lstAlumno = x
      );
  }

  registraTesis(){
    this.submitted = true;

    if(this.formsRegistra.invalid) {
      //Si existe error en todo el formulario
      return;
    }

    this.submitted = false;

    this.tesisService.registraTesis(this.objTesis).subscribe(
      x => {
        Swal.fire('Mensaje', 'Tesis registrada correctamente','info');
      }
    );
  }

  ngOnInit(): void {
  }

}
