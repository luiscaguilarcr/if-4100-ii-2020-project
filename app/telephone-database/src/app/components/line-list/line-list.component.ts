import { Component, OnInit } from '@angular/core';
import { LineService } from '../../services/line.service';
import { Line } from '../../models/line.model';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-line-list',
  templateUrl: './line-list.component.html',
  styleUrls: ['./line-list.component.scss']
})
export class LineListComponent implements OnInit {

  title = 'Registros de líneas';
  list: Line[] = [];
  faEdit = faEdit;
  faTrash = faTrash;
  constructor( private router: Router, private lineService: LineService) {
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

  updateLine(line: Line){
    this.lineService.setLineSelected(line);
    this.router.navigateByUrl('line/update');
    console.log(`update ${line}`);
  }

  deleteLine(line: Line){
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
          const response = await this.lineService.delete(line).toPromise();
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
      const result = await this.lineService.getList().toPromise();
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
