import { Component, OnInit } from '@angular/core';
import { Sala } from 'src/app/models/sala.model';
import { Sede } from 'src/app/models/sede.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2'
import { SalaService } from 'src/app/services/sala.service';
import { UtilService } from 'src/app/services/util.service';
@Component({
  selector: 'app-crud-sala',
  templateUrl: './crud-sala.component.html',
  styleUrls: ['./crud-sala.component.css']
})
export class CrudSalaComponent implements OnInit {

  //Grilla
  salas: Sala [] = [];
  filtro: string= "";
  
  //Sede
  lstsede: Sede [] = [];

    //Json para registrar o actualizar
    sala: Sala = {
      idSala:0,
      numero:"",
      piso:0,
      numAlumnos:0,
      recursos:"",
      estado:1,
      sede:{
        idSede: -1,
        nombre:"-1",
      }
    }

//para verificar que e pulsó el boton
submitted = false;

formsRegistra = new FormGroup({
  validaNumero: new FormControl('', [Validators.required, Validators.pattern('[A-Z0-9]{4,10}')]),
  validaPiso: new FormControl('', [Validators.required,Validators.pattern('[0-9]{1,3}')]),
  validaNumeroAlumnos: new FormControl('', [Validators.required,Validators.pattern('[0-9]{1,4}')]),
  validaRecursos: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z]{2,50}')]),
  validaSede: new FormControl('', [Validators.min(1)]),
});

formsActualiza = new FormGroup({
  validaNumero: new FormControl('', [Validators.required, Validators.pattern('[A-Z0-9]{4,10}')]),
  validaPiso: new FormControl('', [Validators.required,Validators.pattern('[0-9]{1,3}')]),
  validaNumeroAlumnos: new FormControl('', [Validators.required,Validators.pattern('[0-9]{1,4}')]),
  validaRecursos: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z]{2,50}')]),
  validaSede: new FormControl('', [Validators.min(1)]),
  ValidaEstado: new FormControl('', [Validators.min(0)]),
});

constructor( private sedeService:UtilService, private salaService:SalaService) {
  this.sedeService.listaSede().subscribe(
    x => this.lstsede = x
  );
 }

  ngOnInit(): void {
  }

  consultaCrud(){
    this.salaService.consultaPorNumeroCrud(this.filtro==""?"A":this.filtro).subscribe(
      x => this.salas = x
    );
  }

  registraCrud(){
    this.submitted = true;
    
   //finaliza el método si hay un error
   if (this.formsRegistra.invalid){
    return;
   }

   this.submitted = false;

   this.salaService.insertaCrud(this.sala).subscribe(
    x => {

      
      document.getElementById("btn_reg_cerrar")?.click();
      Swal.fire('Mensaje', x.mensaje,'success');
      this.salaService.consultaPorNumeroCrud(this.filtro==""?"A":this.filtro).subscribe(
        x => this.salas = x
      );
    }
   );
   
   //limpiar los comobobox


    this.sala = {
      idSala:0,
      numero:"",
      piso:0,
      numAlumnos:0,
      recursos:"",
      estado:1,
      sede:{
        idSede: -1,
        nombre:"-1",
      }
    }
  }

  actualizaEstado(obj:Sala){
    obj.estado= obj.estado== 1? 0: 1;
    this.salaService.actualizaCrud(obj).subscribe();
  }
  
  buscarSala(obj:Sala){
    this.sala = obj;
   
  }



actualizaCrud(){
this.submitted = true;

//finaliza el método si hay un error
if (this.formsActualiza.invalid){
 return;
}

this.submitted = false;

this.salaService.actualizaCrud(this.sala).subscribe(
        x => {
             document.getElementById("btn_act_cerrar")?.click();
             Swal.fire('Mensaje', x.mensaje,'info'); 
             this.salaService.consultaPorNumeroCrud(this.filtro==""?"A":this.filtro).subscribe(
                 x => this.salas = x
             ); 
        }
  );

   //limpiar los comobobox
  

   this.sala = {
     idSala:0,
     numero:"",
     piso:0,
     numAlumnos:0,
     recursos:"",
     estado:1,
     sede:{
       idSede: -1,
       nombre:"-1",
     }
   }
}


eliminaCrud(obj:Sala){
      Swal.fire({
        title: '¿Desea eliminar?',
        text: "Los cambios no se van a revertir",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, Eliminar.',
        cancelButtonText: 'No, Cancelar'
      }).then((result) => {
          if (result.isConfirmed) {
            
            this.salaService.eliminaCrud(obj.idSala || 0).subscribe(
                x  =>  {
                      Swal.fire('Mensaje',x.mensaje,'success');
                      this.salaService.consultaPorNumeroCrud(this.filtro==""?"A":this.filtro).subscribe(
                            x => this.salas = x
                      ); 
                } 
            );
            
          }
      })
}

}

