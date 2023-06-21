import { Component, OnInit } from '@angular/core';
import { Subscriber } from 'rxjs';
import { Sala } from 'src/app/models/sala.model';
import { Sede } from 'src/app/models/sede.model';
import { SalaService } from 'src/app/services/sala.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-agregar-sala',
  templateUrl: './agregar-sala.component.html',
  styleUrls: ['./agregar-sala.component.css']
})
export class AgregarSalaComponent implements OnInit {

  lstsede: Sede [] = [];
  objSala: Sala = {
    sede:{
      idSede: -1,
    }

  };


  constructor( private sedeService:UtilService, private sala:SalaService) {
    this.sedeService.listaSede().subscribe(
      x => this.lstsede = x
    );
   }

   registra(){
      this.sala.registraSala(this.objSala).subscribe(
          x => Swal.fire({icon: 'info',title: 'Registro',text: x.errores})

      );
   }

  ngOnInit(): void {
  }

}
