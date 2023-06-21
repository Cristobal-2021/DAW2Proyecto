import { Component, OnInit } from '@angular/core';
import { Categoria } from 'src/app/models/categoria.model';
import { Libro } from 'src/app/models/libro.model';
import { LibroService } from 'src/app/services/libro.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-consulta-libro',
  templateUrl: './consulta-libro.component.html',
  styleUrls: ['./consulta-libro.component.css']
})
export class ConsultaLibroComponent implements OnInit {

  titulo:string="";
  serie:string="";
  idCategoria:number= -1;
  estado:boolean = true;


  lstCategoria:Categoria [] = []; 
  

  lstlibro: Libro[] = [];

  constructor(private categoriaService:UtilService, private libroService:LibroService) { 

    this.categoriaService.listaCategoria().subscribe(
      x => this.lstCategoria = x
  );
  
  }


  consultaLibro(){
    this.libroService.consultaLibro(this.titulo, this.serie, this.idCategoria, this.estado?1:0).subscribe(
      x => {
        Swal.fire('Mensaje', x.mensaje,'info');
        this.lstlibro = x.lista
      }  
    );
   }

  ngOnInit(): void {
  }

}
