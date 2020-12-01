import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../../services/service.service';
import { Service } from '../../models/service.model';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';

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
  constructor( private ServiceService: ServiceService) {
  }

  ngOnInit(): void {
    this.refreshList();
  }

  updateService(service: Service){
    console.log(`update ${service}`);
  }

  deleteService(service: Service){
    return this.ServiceService.delete(service).toPromise()
    .then(result => {
      console.log(result);
      this.refreshList();
    })
    .catch(msg => console.log(msg));
  }

  refreshList(){
    return this.ServiceService.getList().toPromise()
    .then( result => {
      return this.list = result;
    })
    .catch(msg => console.log(msg));
  }
}

