import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Line } from '../../models/line.model';
import { LineService } from '../../services/line.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { LineCustomer } from '../../models/line_customer.model';
import { LineCustomerService } from '../../services/lineCustomer.service';

@Component({
  selector: 'app-line-add',
  templateUrl: './line-add.component.html',
  styleUrls: ['./line-add.component.scss']
})
export class LineAddComponent implements OnInit {

  form: FormGroup;
  title = 'Crear una nueva línea';
  line: Line;
  lineCustomer: LineCustomer;
  private list = ['ADSL', 'Básica', 'RDSI'];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private lineService: LineService,
    private lineCustomerService: LineCustomerService,
     ) {
    this. line = this.buildLine();
    this.lineCustomer = new LineCustomer();
    this.form = this.createForm();
  }
  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
      customerFirstName : [this.lineCustomer.firstName,   [Validators.required]],
      customerLastName : [this.lineCustomer.lastName,   [Validators.required]],
      customerAddress : [this.lineCustomer.address],
      customerEmail :  [this.lineCustomer.email,   [Validators.required]],
      customerId : [this.lineCustomer.id,   [Validators.required]],
      telephoneNumber:   [this.line.telephoneNumber,   [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      type:  [this.line.type,   [Validators.required]],
    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLine(): Line {
    const line = new Line();
    line.telephoneNumber = undefined;
    line.status = 'A';
    line.type   = 0;
    return line;
  }
  ngOnInit(): void { }
  /**
   * Return if the input is valid or not.
   * @param input input's name.
   */
  invalidInput(input: string): boolean {
    const control = this.form.get(input);
    if ( control ) {
      return control.invalid && control.touched;
    } else {
      return true;
    }
  }
  /**
   * Summit action.
   */
  submit() {
    /* Validate inputs */
    if (this.form.invalid) {
      return Object.values(this.form.controls)
        .forEach(control => control.markAsTouched());
    }
    /* Get values */
    this.loadLineModel();
    this.line.status = 'A';
    this.line.pointsQuantity = 0;
    console.log(this.line);
    /* Do request */
    return this.lineService.add(this.line).subscribe(response => {
      return this.lineCustomerService.add(this.lineCustomer).subscribe(response => {
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Línea creada exitosamente',
        showConfirmButton: false,
        timer: 1500
      });
      this.router.navigateByUrl('/line/list');
      });
     }, err => {
      if (err.status === 400 ){
        /* Bad request */
        console.log(err.headers.get('error_code'));
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: `Hubo un problema al agregar la línea. Código de error: ${err.headers.get('error_code') }!`,
        });
      } else if (err.status >= 500){
        /* Server error */
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Hubo un problema al agregar la línea. Intenta más tarde!',
        });
      }
     });
  }
  /**
   * Load the line model with form's values.
   */
  private loadLineModel(): void {
    let control;
    control = this.form.get('telephoneNumber');
    if ( control ) { this.line.telephoneNumber = control.value; } else { console.log('Telephone Number value not loaded.'); }
    if ( control ) { this.lineCustomer.telephoneNumber = control.value; } else { console.log('Line value not loaded.'); }
    control = this.form.get('type');
    if ( control ) { this.line.type = this.list.indexOf(control.value); } else { console.log('Line value not loaded.'); }
    control = this.form.get('customerId');
    if ( control ) { this.lineCustomer.id = control.value; } else { console.log('Customer Telephone Number not loaded.'); }
    control = this.form.get('customerFirstName');
    if ( control ) { this.lineCustomer.firstName = control.value; } else { console.log('Customer first name not loaded.'); }
    control = this.form.get('customerLastName');
    if ( control ) {
      this.lineCustomer.lastName = control.value !== '' ? control.value : 'No registrado';
    } else { console.log('Customer last name value not loaded.'); }
    control = this.form.get('customerEmail');
    if ( control ) { this.lineCustomer.email = control.value; } else { console.log('Customer email value not loaded.'); }
    control = this.form.get('customerAddress');
    if ( control ) { this.lineCustomer.address = control.value; } else { console.log('Customer address not loaded.'); }
  }
}
