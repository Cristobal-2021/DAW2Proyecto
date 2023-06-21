import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Alumno } from 'src/app/models/alumno.model';
import { Tesis } from 'src/app/models/tesis.model';
import { crudTesisService } from 'src/app/services/crudTesis.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-crud-tesis',
  templateUrl: './crud-tesis.component.html',
  styleUrls: ['./crud-tesis.component.css']
})
export class CrudTesisComponent implements OnInit {

  tesisV: Tesis[] = [];
  filtro: string = "";

  alumnos: Alumno[] = [];

  tesis: Tesis = { 
    idTesis: 0,
    titulo: "",
    tema: "",
    estado: 1,
    fechaCreacion: new Date(),
    alumno: {
			idAlumno: -1,
			nombres: "",
			apellidos: "",
			telefono: "",
			dni: "",
			correo: "",
			fechaNacimiento: new Date(),
			fechaRegistro: new Date(),
			estado: 1,
			pais: {
				idPais: -1,
				iso: "",
				nombre: ""
			}
    }
  };

  submitted = false;

  formsRegistra = new FormGroup({
    validaTitulo: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{20,200}')]),
    validaTema: new FormControl('', [Validators.required,Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{6,100}')]),
    validaFechaCreacion: new FormControl('', [Validators.required]),
    validaAlumno: new FormControl('', [Validators.min(1)])
  });

  formsActualiza = new FormGroup({
    validaTitulo: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{20,200}')]),
    validaTema: new FormControl('', [Validators.required,Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{6,100}')]),
    validaFechaCreacion: new FormControl('', [Validators.required]),
    validaAlumno: new FormControl('', [Validators.min(1)]),
    validaEstado: new FormControl('', [Validators.min(0)])
  });

  constructor(private utilService: UtilService, private crudTesisService: crudTesisService) {
    this.utilService.listaAlumno().subscribe(
      x => this.alumnos = x
    );
  }

  ngOnInit(): void {
  }

  consulta() {
    this.crudTesisService.consultaPorTitulo(this.filtro == ""? "todos":this.filtro).subscribe(
      x => this.tesisV = x
    );
  }

  inserta(){
    this.submitted = true;

    if(this.formsRegistra.invalid) {
      //Si existe error en todo el formulario
      return;
    }

    this.submitted = false;

    this.crudTesisService.inserta(this.tesis).subscribe(
      x => {
        Swal.fire('Mensaje', x.mensaje,'info');
        document.getElementById("btn_reg_cerrar")?.click();
        this.crudTesisService.consultaPorTitulo(this.filtro == ""? "todos":this.filtro).subscribe(
          x => this.tesisV = x
        );
      }
    );
  }

  actualizaEstado(obj:Tesis){
    obj.estado = obj.estado == 1? 0:1;  
    this.crudTesisService.actualiza(obj).subscribe();
  }

  busca(obj:Tesis){
    this.tesis = obj;

    this.utilService.listaAlumno().subscribe(
      x => this.alumnos = x
    );
  }

  actualiza(){
    this.submitted = true;

    if(this.formsActualiza.invalid) {
      //Si existe error en todo el formulario
      return;
    }

    this.crudTesisService.actualiza(this.tesis).subscribe(
      x => {
        Swal.fire('Mensaje', x.mensaje,'info');
        document.getElementById("btn_act_cerrar")?.click();
        this.crudTesisService.consultaPorTitulo(this.filtro == ""? "todos":this.filtro).subscribe(
          x => this.tesisV = x
        );
      }
    );
  }

elimina(obj:Tesis){
  Swal.fire({
    title: '¿Desea eliminar?',
    text: "Los cambios no se van a revertir",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Sí, elimina.',
    cancelButtonText: 'No, cancelar'
  }).then((result) => {
    if (result.isConfirmed) {        
      this.crudTesisService.elimina(obj.idTesis || 0).subscribe(
        x  => {
          Swal.fire('Mensaje',x.mensaje,'success')
          this.crudTesisService.consultaPorTitulo(this.filtro == ""? "todos":this.filtro).subscribe(
            x => this.tesisV = x
          );
        }
      );        
    }
  });
}

}
