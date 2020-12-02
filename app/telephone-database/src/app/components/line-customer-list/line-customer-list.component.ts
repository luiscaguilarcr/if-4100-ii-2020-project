import { Component, OnInit } from '@angular/core';
import { LineCustomer } from '../../models/line_customer.model';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import { LineCustomerService } from 'src/app/services/lineCustomer.service';

@Component({
  selector: 'app-line-customer-list',
  templateUrl: './line-customer-list.component.html',
 // styleUrls: ['./line-customer-list.component.scss']
})
export class LineCustomerListComponent implements OnInit {

  title = 'Registros Clientes';
  list: LineCustomer[] = [];
  faEdit = faEdit;
  faTrash = faTrash;
  constructor( private lineCustomerService: LineCustomerService) {
  }

  ngOnInit(): void {
    this.refreshList();
  }

  updateLineCustomer(lineCustomer: LineCustomer){
    console.log(`update ${lineCustomer}`);
  }

  deleteLineCustomer(lineCustomer: LineCustomer){
    return this.lineCustomerService.delete(lineCustomer).toPromise()
    .then(result => {
      console.log(result);
      this.refreshList();
    })
    .catch(msg => console.log(msg));
  }

  refreshList(){
    return this.lineCustomerService.getList().toPromise()
    .then( result => {
      return this.list = result;
    })
    .catch(msg => console.log(msg));
  }
}
