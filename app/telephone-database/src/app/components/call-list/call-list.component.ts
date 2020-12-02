import { Component, OnInit } from '@angular/core';
import { CallService } from '../../services/call.service';
import { Call } from '../../models/call.model';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-call-list',
  templateUrl: './call-list.component.html',
  styleUrls: ['./call-list.component.scss']
})
export class CallListComponent implements OnInit {

  title = 'Registros de llamadas';
  list: Call[] = [];
  faEdit = faEdit;
  faTrash = faTrash;
  constructor( private callService: CallService) {
  }

  ngOnInit(): void {
    Swal.fire({
      icon: 'info',
      title: 'Espera, unos momentos...',
      text: 'Se están cargando los registros de líneas!',
      showConfirmButton: false,
      allowOutsideClick: false,
    });
    this.refreshList();
  }

  updateCall(call: Call){
    console.log(`update ${call}`);
  }

 deleteCall(call: Call){
    return this.callService.delete(call).toPromise()
    .then(result => {
      console.log(result);
      this.refreshList();
    })
    .catch(msg => console.log(msg));
  }

  async refreshList(){
    try {
      const result = await this.callService.getList().toPromise();
      Swal.close();
      return this.list = result;
    } catch (msg) {
      console.log(msg);
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Hubo un problema al obtener la información. Intenta más tarde!',
      });
    }
  }
}

