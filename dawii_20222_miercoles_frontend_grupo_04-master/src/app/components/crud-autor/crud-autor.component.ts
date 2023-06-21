import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Autor } from 'src/app/models/autor.model';
import { Grado } from 'src/app/models/grado.model';
import { AutorService } from 'src/app/services/autor.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2';
 
@Component({
  selector: 'app-crud-autor',
  templateUrl: './crud-autor.component.html',
  styleUrls: ['./crud-autor.component.css']
})
export class CrudAutorComponent implements OnInit {
  //Ng model
  nombre:string="";
  apellido:string="";
  telefono:string="";
  idGrado:number = -1;
  estado:boolean = true;
 
  //Pais
  lstGrado: Grado[] = [];
 
  //lista
  lstAutor: Autor[] = [];
 
  autor: Autor = {
    grado:{
      idGrado: -1,
    }
  }
 
  submitted = false;
 
  formsRegistra = new FormGroup({
    validaNombre: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaApellido: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaFecha: new FormControl('', [Validators.required]),
    validaTelefono: new FormControl('', [Validators.required,Validators.pattern('[0-9]{9}')]),
    validaGrado: new FormControl('', [Validators.min(1)]),
 
  });
 
  formsActualiza = new FormGroup({
    validaNombre: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaApellido: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaFecha: new FormControl('', [Validators.required]),
    validaTelefono: new FormControl('', [Validators.required,Validators.pattern('[0-9]{9}')]),
    validaGrado: new FormControl('', [Validators.min(1)]),
  });
 
  constructor(private utilService: UtilService, private autorService: AutorService) {
    this.utilService.listaGrado().subscribe(
      x => this.lstGrado = x
    );
   }
 
  actualizaEstado(obj:Autor){
    obj.estado = obj.estado == 1? 0 : 1;  
    this.autorService.actualizaAutorCrud(obj).subscribe();
  }
 
   consultaAutor(){
    this.autorService.consultaAutor(this.nombre,this.apellido, this.telefono, this.idGrado, this.estado?1:0).subscribe(
      x => {
        Swal.fire('Mensaje', x.mensaje,'info');
        this.lstAutor = x.lista
      }  
    );
   }
 
 
   busca(obj:Autor){
    this.autor = obj;
  }
 
   elimina(idAutor?:number){
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
         
          this.autorService.eliminaAutorCrud(idAutor || 0).subscribe(
              x  =>  {
                Swal.fire('Mensaje',x.mensaje,'success');
                this.autorService.consultaAutor(this.nombre,this.apellido, this.telefono, this.idGrado, this.estado?1:0).subscribe(
                  x => {
                    this.lstAutor = x.lista
                  }
                  );
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
 
    this.autorService.registroAutorCrud(this.autor).subscribe(
        x => {
                if(x.mensaje!=null){
                document.getElementById("btn_reg_cerrar")?.click();
                Swal.fire('Mensaje', x.mensaje,'success');
                this.autorService.consultaAutor(this.nombre,this.apellido, this.telefono, this.idGrado, this.estado?1:0).subscribe(
                  x => {
                    this.lstAutor = x.lista
                  }
                  );
                }
                else{
                  Swal.fire('Mensaje Error',x.error,'error');
                }
        }
    );
 
    //limpiar los componentes del formulario a través de los ngModel
      this.autor = {
        grado:{
            idGrado: -1,
        }
    }
  }
 
  actualiza(){
    this.submitted = true;
 
    //finaliza el método si hay un error
    if (this.formsActualiza.invalid){
     return;
    }
 
    this.submitted = false;
 
 
    this.autorService.actualizaAutorCrud(this.autor).subscribe(
      x => {
              if(x.mensaje!=null){
              document.getElementById("btn_reg_cerrar")?.click();
              Swal.fire('Mensaje', x.mensaje,'success');
              this.autorService.consultaAutor(this.nombre,this.apellido, this.telefono, this.idGrado, this.estado?1:0).subscribe(
                x => {
                  this.lstAutor = x.lista
                }
                );
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
 

