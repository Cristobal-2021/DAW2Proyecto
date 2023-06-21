import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Alumno } from 'src/app/models/alumno.model';
import { Pais } from 'src/app/models/pais.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-crud-alumno',
  templateUrl: './crud-alumno.component.html',
  styleUrls: ['./crud-alumno.component.css']
})
export class CrudAlumnoComponent implements OnInit {
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

   alumno: Alumno = {
    pais:{
        idPais: -1,
    }
    }

  alumnoRegistra: Alumno = {
      pais:{
          idPais: -1,
      }
      }
  submitted = false;
  submittedA = false;

  formsRegistra = new FormGroup({
    validaNombre: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaApellido: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaDni: new FormControl('', [Validators.required,Validators.pattern('[0-9]{8}')]),
    validaTelefono: new FormControl('', [Validators.required,Validators.pattern('[0-9]{9}')]),
    validaCorreo: new FormControl('', [Validators.required,Validators.pattern('[^@]+@[^@]+\.[a-zA-Z]{2,}')]),
    validaFecha: new FormControl('', [Validators.required]),
    validaPais: new FormControl('', [Validators.min(1)]),

  });

  formsActualiza = new FormGroup({
    validaNombreA: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaApellidoA: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaDniA: new FormControl('', [Validators.required,Validators.pattern('[0-9]{8}')]),
    validaTelefonoA: new FormControl('', [Validators.required,Validators.pattern('[0-9]{9}')]),
    validaCorreoA: new FormControl('', [Validators.required,Validators.pattern('[^@]+@[^@]+\.[a-zA-Z]{2,}')]),
    validaFechaA: new FormControl('', [Validators.required]),
    validaPaisA: new FormControl('', [Validators.min(1)]),
  });

   constructor(private utilService: UtilService, private alumnoService: AlumnoService) {
     this.utilService.listaPais().subscribe(
       x => this.lstPaises = x
     );

    }
 
    consultaAlumno(){
     this.alumnoService.consultaAlumnoCrud(this.nombre, this.apellido, this.dni, this.idPais, this.estado?1:0).subscribe(
       x => {
         this.lstAlumnos = x
       }  
     );
    }
    
    actualizaEstado(obj:Alumno){
      obj.estado = obj.estado == 1? 0 : 1;  
      this.alumnoService.actualizaAlumnoCrud(obj).subscribe();
    }

  busca(obj:Alumno){

    this.alumnoService.obtenerAlumnoCrud(obj.idAlumno!).subscribe(
      x => this.alumno = x
    );

    this.utilService.listaPais().subscribe(
      x => this.lstPaises = x
    );
  }

  elimina(idAlumno?:number){
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
          
          this.alumnoService.eliminaAlumnoCrud(idAlumno || 0).subscribe(
              x  =>  {
                this.lstAlumnos = this.lstAlumnos.filter((alumno:any) => alumno.idAlumno != idAlumno);

                    Swal.fire('Mensaje',x.mensaje,'success');

              } 
          );
          
        }
    })
  }

  registra(){
    this.submitted = true;

    //finaliza el método si hay un error
    if (this.formsRegistra.invalid){
    return;
    }

    this.submitted = false;

    this.alumnoService.registroAlumnoCrud(this.alumnoRegistra).subscribe(
        x => { 
                if(x.mensaje!=null){
                document.getElementById("btn_reg_cerrar")?.click();
                Swal.fire('Mensaje', x.mensaje,'success'); 
                this.consultaAlumno();
                }
                else{
                  Swal.fire('Mensaje Error',x.error,'error');
                }
        }
    );

    //limpiar los componentes del formulario a través de los ngModel
      this.alumnoRegistra = {
        pais:{
            idPais: -1,
        }
    }
  }

  actualiza(){
    this.submittedA = true;

    if (this.formsActualiza.invalid){
      return;
    }

    this.submittedA = false;

    //finaliza el método si hay un error
      this.alumnoService.actualizaAlumnoCrud(this.alumno).subscribe(
        x => {
            if(x.mensaje!=null){
             document.getElementById("btn_act_cerrar")?.click();
             Swal.fire('Mensaje', x.mensaje,'success'); 
             this.consultaAlumno();
            }
            else{
              Swal.fire('Mensaje Error',x.error,'error');
            }
        }
    );
  }

   ngOnInit(): void {
   }

}
