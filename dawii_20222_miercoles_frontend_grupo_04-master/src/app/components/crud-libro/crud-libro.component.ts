import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Categoria } from 'src/app/models/categoria.model';
import { Libro } from 'src/app/models/libro.model';
import { LibroService } from 'src/app/services/libro.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crud-libro',
  templateUrl: './crud-libro.component.html',
  styleUrls: ['./crud-libro.component.css']
})
export class CrudLibroComponent implements OnInit {

  //Ng model
  titulo: string = "";
  serie: string = "";
  idCategoria: number = -1;
  estado: boolean = true;

  //Categoria
  lstCategoria: Categoria[] = [];

  //lista
  lstlibro: Libro[] = [];

  libro: Libro = {
    categoria: {
      idCategoria: -1,
    }
  }


  libroRegistra: Libro = {
    categoria: {
      idCategoria: -1,
    }
  }

  submitted = false;
  submittedA = false;

  formsRegistra = new FormGroup({
    validaTitulo: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaAnio: new FormControl('', [Validators.required,Validators.pattern('[0-9]{4}')]),
    validaSerie: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{13}')]),
    validaCategoria: new FormControl('', [Validators.min(1)]),

  });

  formsActualiza = new FormGroup({
    validaTituloAc: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{3,30}')]),
    validaAnioAc: new FormControl('', [Validators.required,Validators.pattern('[0-9]{4}')]),
    validaSerieAc: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9 ]{13}')]),
    validaCategoriaAc: new FormControl('', [Validators.min(1)]),
  });


  constructor(private utilService: UtilService, private libroService: LibroService) {
    this.utilService.listaCategoria().subscribe(
      x => this.lstCategoria = x
    );

  }

  consultaLibro() {
    this.libroService.consultaLibroCrud(this.titulo, this.serie, this.idCategoria, this.estado ? 1 : 0).subscribe(
      x => {
        this.lstlibro = x.lista
      }
    );
  }

  actualizaEstado(obj: Libro) {
    obj.estado = obj.estado == 1 ? 0 : 1;
    this.libroService.actualizaLibroCrud(obj).subscribe();
  }


  busca(obj: Libro) {

    this.libroService.obtenerLibroCrud(obj.idLibro!).subscribe(
      x => this.libro = x
    );

    this.utilService.listaCategoria().subscribe(
      x => this.lstCategoria = x
    );
  }

  elimina(idLibro?: number) {
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

        this.libroService.eliminaLibroCrud(idLibro || 0).subscribe(
          x => {
            this.lstlibro = this.lstlibro.filter((libro: any) => libro.idLibro != idLibro);

            Swal.fire('Mensaje', x.mensaje, 'success');

          }
        );

      }
    })
  }


  registra() {
    this.submitted = true;

    //finaliza el método si hay un error
    if (this.formsRegistra.invalid) {
      return;
    }

    this.submitted = false;

    this.libroService.registroLibroCrud(this.libroRegistra).subscribe(
      x => {
        if (x.mensaje != null) {
          document.getElementById("btn_reg_cerrar")?.click();
          Swal.fire('Mensaje', x.mensaje, 'success');
          this.consultaLibro();
        }
        else {
          Swal.fire('Mensaje Error', x.error, 'error');
        }
      }
    );

    //limpiar los componentes del formulario a través de los ngModel
    this.libroRegistra = {
      categoria: {
        idCategoria: -1,
      }
    }
  }


  actualiza() {
    this.submittedA = true;

    if (this.formsActualiza.invalid) {
      return;
    }

    this.submittedA = false;

    //finaliza el método si hay un error
    this.libroService.actualizaLibroCrud(this.libro).subscribe(
      x => {
        if (x.mensaje != null) {
          document.getElementById("btn_act_cerrar")?.click();
          Swal.fire('Mensaje', x.mensaje, 'success');
          this.consultaLibro();
        }
        else {
          Swal.fire('Mensaje Error', x.error, 'error');
        }
      }
    );
  }


















  ngOnInit(): void {
  }

}
