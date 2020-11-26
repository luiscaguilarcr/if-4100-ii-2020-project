import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  title = 'Telephone Company';

  constructor( private router: Router) { } /* String name    name: string*/

  ngOnInit(): void {
  }

  showPage( url: string): void{
    console.log('Mostrando pagina: ' + url);
    this.router.navigateByUrl(url);               /* url = line/add */
  }
}
