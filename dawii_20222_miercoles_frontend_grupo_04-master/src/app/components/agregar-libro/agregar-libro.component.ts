import { Component, OnInit } from '@angular/core';
import { Categoria } from 'src/app/models/categoria.model';
import { Libro } from 'src/app/models/libro.model';
import { LibroService } from 'src/app/services/libro.service';
import { UtilService } from 'src/app/services/util.service';


@Component({
  selector: 'app-agregar-libro',
  templateUrl: './agregar-libro.component.html',
  styleUrls: ['./agregar-libro.component.css']
})
export class AgregarLibroComponent implements OnInit {

  lstCategoria:Categoria [] = []; 
  objLibro:Libro = {
    categoria:{
      idCategoria: -1
    }
  };


  constructor(private categoriaService:UtilService, private libroService:LibroService) { 

  this.categoriaService.listaCategoria().subscribe(
    x => this.lstCategoria = x
);




}

registra(){
this.libroService.registraLibro(this.objLibro).subscribe(
    x => alert(x.errores)
);
}

  ngOnInit(): void {
  }

}
