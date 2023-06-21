import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Pais } from 'src/app/models/pais.model';
import { Proveedor } from 'src/app/models/proveedor.model';
import { ProveedorService } from 'src/app/services/proveedor.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-agregar-proveedor',
  templateUrl: './agregar-proveedor.component.html',
  styleUrls: ['./agregar-proveedor.component.css']
})
export class AgregarProveedorComponent implements OnInit {


  lstPaises: Pais[] = [];
  objProveedor: Proveedor = {
    pais: {
      idPais: -1
    }
  };

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

  constructor(private utilService: UtilService, private proveedorService: ProveedorService) {

    this.utilService.listaPais().subscribe(
      x => this.lstPaises = x
    )

  }

  registra() {

    this.submitted = true;

    if (this.formsRegistra.invalid) {
      return;
    }

    this.submitted = false;

    this.proveedorService.registraProveedor(this.objProveedor).subscribe(
      x => { Swal.fire('Mensaje', x.errores[0], 'info'); }
    )
  }

  ngOnInit(): void {
  }

}
