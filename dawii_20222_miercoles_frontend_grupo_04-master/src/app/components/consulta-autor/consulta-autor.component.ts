import { Component, OnInit } from '@angular/core';
import { Autor } from 'src/app/models/autor.model';
import { Grado } from 'src/app/models/grado.model';
import { AutorService } from 'src/app/services/autor.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-consulta-autor',
  templateUrl: './consulta-autor.component.html',
  styleUrls: ['./consulta-autor.component.css']
})
export class ConsultaAutorComponent implements OnInit {
 
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
   
    constructor(private utilService: UtilService, private autorService: AutorService) {
      this.utilService.listaGrado().subscribe(
        x => this.lstGrado = x
      );
     }
   
     consultaAutor(){
      this.autorService.consultaAutor(this.nombre,this.apellido, this.telefono, this.idGrado, this.estado?1:0).subscribe(
        x => {
          Swal.fire('Mensaje', x.mensaje,'info');
          this.lstAutor = x.lista
        }  
      );
     }
   
   
    ngOnInit(): void {
    }
   
  }
  