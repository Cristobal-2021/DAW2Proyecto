import { Component, OnInit } from '@angular/core';
import { Pais } from 'src/app/models/pais.model';
import { Proveedor } from 'src/app/models/proveedor.model';
import { ProveedorService } from 'src/app/services/proveedor.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-consulta-proveedor',
  templateUrl: './consulta-proveedor.component.html',
  styleUrls: ['./consulta-proveedor.component.css']
})
export class ConsultaProveedorComponent implements OnInit {

  razonsocial: string = "";
  ruc: string = "";
  estado: boolean = true;
  selPais: number = -1;

  //Paises
  paises: Pais[] = [];

  //Tabla
  proveedores: Proveedor[] = [];

  constructor(private utilService: UtilService, private proveedorService: ProveedorService) {
    this.utilService.listaPais().subscribe(
      x => this.paises = x
    );
  }

  consultaProveedor() {
    this.proveedorService.listaProveedor(this.razonsocial, this.ruc, this.estado ? 1 : 0, this.selPais).subscribe(
      x => {
        this.proveedores = x.lista;
        Swal.fire('Mensaje', x.mensaje, 'info');
      }
    );
  }




  ngOnInit(): void {
  }

}
