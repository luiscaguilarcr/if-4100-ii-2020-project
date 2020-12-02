import { Component, OnInit } from '@angular/core';
import { CallService } from '../../services/call.service';
import { Call } from '../../models/call.model';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';

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

  refreshList(){
    return this.callService.getList().toPromise()
    .then( result => {
      return this.list = result;
    })
    .catch(msg => console.log(msg));
  }
}

