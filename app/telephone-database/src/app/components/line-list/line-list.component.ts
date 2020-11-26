import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-line-list',
  templateUrl: './line-list.component.html',
  styleUrls: ['./line-list.component.scss']
})
export class LineListComponent implements OnInit {

  list = ['a', 'b'];
  constructor() { }

  ngOnInit(): void {
  }

}
