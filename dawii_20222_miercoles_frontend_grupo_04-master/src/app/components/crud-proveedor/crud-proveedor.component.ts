import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Pais } from 'src/app/models/pais.model';
import { Proveedor } from 'src/app/models/proveedor.model';
import { ProveedorService } from 'src/app/services/proveedor.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crud-proveedor',
  templateUrl: './crud-proveedor.component.html',
  styleUrls: ['./crud-proveedor.component.css']
})
export class CrudProveedorComponent implements OnInit {

  //Para la Grilla
  proveedores: Proveedor[] = [];
  filtro: string = "";

  lstPaises: Pais[] = [];

  //Json para registrar o actualizar
  proveedor: Proveedor = {
    idProveedor: 0,
    razonsocial: "",
    ruc: "",
    direccion: "",
    celular: "",
    contacto: "",
    estado: 1,
    pais: {
      idPais: -1
    }
  };

  //para verificar que se pulsó el botón( true = se pulsó "Enviar" : false = no se pulsó "Enviar")
  submitted = false;

  //Validadores para el modal de registrar
  formsRegistra = new FormGroup({
    validaRazonSocial: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9\\s]{3,30}')]),
    validaRuc: new FormControl('', [Validators.required, Validators.pattern('[0-9]{11}')]),
    validaDireccion: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9.\\s]{3,30}')]),
    validaCelular: new FormControl('', [Validators.required, Validators.pattern('9[0-9]{8}')]),
    validaContacto: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ\\s]{3,30}')]),
    validaPais: new FormControl('', [Validators.min(1)]),
  });

  //Validadores para el modal de actualizar
  formsActualiza = new FormGroup({
    validaRazonSocial: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9\\s]{3,30}')]),
    validaRuc: new FormControl('', [Validators.required, Validators.pattern('[0-9]{11}')]),
    validaDireccion: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ0-9.\\s]{3,30}')]),
    validaCelular: new FormControl('', [Validators.required, Validators.pattern('9[0-9]{8}')]),
    validaContacto: new FormControl('', [Validators.required, Validators.pattern('[a-zA-ZáéíóúÁÉÍÓÚñ\\s]{3,30}')]),
    validaPais: new FormControl('', [Validators.min(1)]),
    validaEstado: new FormControl('', [Validators.min(0)]),
  });

  constructor(private utilService: UtilService, private proveedorService: ProveedorService) {

    this.utilService.listaPais().subscribe(
      x => this.lstPaises = x
    )
  }

  consulta() {
    this.proveedorService.consultaProveedor(this.filtro == "" ? "todos" : this.filtro).subscribe(
      x => this.proveedores = x
    );
  }

  registra() {

    this.submitted = true;

    if (this.formsRegistra.invalid) {
      return;
    }

    this.submitted = false;

    this.proveedorService.registraProveedorCrud(this.proveedor).subscribe(
      x => {
        document.getElementById("btn_reg_cerrar")?.click();
        Swal.fire('Mensaje', x.mensaje, 'info');
        this.proveedorService.consultaProveedor(this.filtro == "" ? "todos" : this.filtro).subscribe(
          x => this.proveedores = x
        );
      }
    );

    this.proveedor = {
      idProveedor: 0,
      razonsocial: "",
      ruc: "",
      direccion: "",
      celular: "",
      contacto: "",
      estado: 1,
      pais: {
        idPais: -1
      }
    }


  }

  busca(obj: Proveedor) {

    console.log("obj.nombre>>> " + obj.razonsocial);
    this.proveedor = obj;

  }

  actualiza() {

    this.submitted = true;

    if (this.formsActualiza.invalid) {
      return;
    }

    this.submitted = false;

    this.proveedorService.actualizaProveedor(this.proveedor).subscribe(
      x => {
        Swal.fire('Mensaje', x.mensaje, 'info');
        /* document.getElementById("btn_act_limpiar")?.click(); */
        document.getElementById("btn_act_cerrar")?.click();
        this.proveedorService.consultaProveedor(this.filtro == "" ? "todos" : this.filtro).subscribe(
          x => this.proveedores = x
        );
      }
    );

    this.proveedor = {
      idProveedor: 0,
      razonsocial: "",
      ruc: "",
      direccion: "",
      celular: "",
      contacto: "",
      estado: 1,
      pais: {
        idPais: -1
      },
    }

  }

  actualizaEstado(obj: Proveedor) {
    obj.estado = obj.estado == 1 ? 0 : 1;
    this.proveedorService.actualizaProveedor(obj).subscribe()
  }

  elimina(obj: Proveedor) {
    Swal.fire({
      title: '¿Desea eliminar?',
      text: "Los cambios no se van a revertir",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar proveedor.',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.proveedorService.eliminarProveedor(obj.idProveedor || 0).subscribe(
          x => {
            Swal.fire('Mensaje', x.mensaje, 'success')
            this.proveedorService.consultaProveedor(this.filtro == "" ? "todos" : this.filtro).subscribe(
              x => this.proveedores = x
            );
          }
        );
      }
    })
  }




  ngOnInit(): void {
  }

}
