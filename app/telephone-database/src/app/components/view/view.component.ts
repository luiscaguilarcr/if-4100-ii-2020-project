import { Component, OnInit } from '@angular/core';
import { ViewService } from '../../services/view.service';
import { View } from '../../models/view.model';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})
export class ViewComponent implements OnInit {
  title = 'Información línea-cliente';
  list: [] = [];
  faEdit = faEdit;
  faTrash = faTrash;
  constructor( private router: Router, private viewService: ViewService) {
  }

  ngOnInit(): void {
    Swal.fire({
      icon: 'info',
      title: 'Espera, unos momentos...',
      text: 'Se están cargando la vista!',
      showConfirmButton: false,
      allowOutsideClick: false,
    });
    this.refreshList();
  }

  listFilter(list: any[]): any{
    console.log(`Lista original ${list}`)
    /* clients => { item: View, serviceList: string[] } */
    let clients = [], serviceList = [], alreadyCheck = [];
    /* For each client */
    for (let i=0; i < list.length; i++) {
      let client = list[i];
      if (alreadyCheck.indexOf(client.customerId) >= 0) continue; /* Already registred */
      serviceList = [] /* reset service list */
      alreadyCheck.push(client.customerId);  
      /* Recorrer toda la lista */
      for (let j=i; j<list.length; j++) {
        // console.log(`${list[j].customerId === client.customerId}`);
        if(list[j].customerId === client.customerId){
          serviceList.push(client.name);
          console.log(`Agrega servicio ${ client.name} al cliente  ${client.customerFirstName}`)

        }         
      }    
      clients.push({
        serviceList,
        client,
      })
    }
    console.log(clients);
    return clients;
  }

  async refreshList(){
    try {
      const result = await this.viewService.getList().toPromise();
      Swal.close();

      return  this.list = result;
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
