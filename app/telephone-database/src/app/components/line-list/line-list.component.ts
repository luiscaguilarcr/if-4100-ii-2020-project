import { Component, OnInit } from '@angular/core';
import { LineService } from '../../services/line.service';
import { Line } from '../../models/line.model';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';

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
  constructor( private lineService: LineService) {
  }

  ngOnInit(): void {
    this.refreshList();
  }

  updateLine(line: Line){
    console.log(`update ${line}`);
  }

  deleteLine(line: Line){
    return this.lineService.delete(line).toPromise()
    .then(result => {
      console.log(result);
      this.refreshList();
    })
    .catch(msg => console.log(msg));
  }

  refreshList(){
    return this.lineService.getList().toPromise()
    .then( result => {
      return this.list = result;
    })
    .catch(msg => console.log(msg));
  }
}
