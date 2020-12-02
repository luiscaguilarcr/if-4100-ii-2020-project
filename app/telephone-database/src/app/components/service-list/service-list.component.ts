import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../../services/service.service';
import { Service } from '../../models/service.model';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-service-list',
  templateUrl: './service-list.component.html',
 // styleUrls: ['./line-list.component.scss']
})
export class ServiceListComponent implements OnInit {

  title = 'Registros de servicios';
  list: Service[] = [];
  faEdit = faEdit;
  faTrash = faTrash;
  constructor(private router: Router, private serviceService: ServiceService) {
  }

  ngOnInit(): void {
    Swal.fire({
      icon: 'info',
      title: 'Espera, unos momentos...',
      text: 'Se están cargando los registros de servicios!',
      showConfirmButton: false,
      allowOutsideClick: false,
    });
    this.refreshList();
  }

  updateService(service: Service){
    this.serviceService.setServiceSelected(service);
    this.router.navigateByUrl('service/update');
    console.log(`update ${service}`);
  }

  deleteService(service: Service){
    Swal.fire({
      title: '¿Desea eliminar está línea?',
      text: 'Se eliminarán todos los registros del cliente relacionados con esta línea.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar'
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          const response = await this.serviceService.delete(service).toPromise();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Línea eliminada exitosamente!',
            showConfirmButton: false,
            timer: 2000
          });
          console.log(response);
          this.refreshList();
        } catch (msg) {
          console.log(msg);
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Hubo un problema al eliminar la línea. Intenta más tarde!',
          });
        }
      }
    });
  }

  async refreshList(){
    try {
      const result = await this.serviceService.getList().toPromise();
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

