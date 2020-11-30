import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LineCustomer } from '../../models/line_customer.model';

@Component({
  selector: 'app-line-customer-add',
  templateUrl: './line-customer-add.component.html',
  styleUrls: ['./line-customer-add.component.scss']
})
export class LineCustomerAddComponent implements OnInit {

  form: FormGroup;
  title = 'Víncular usuario con línea';
  lineCustomer: LineCustomer;

  constructor( private formBuilder: FormBuilder ) {
    this. lineCustomer = this.buildLineCustomer();
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
    telephoneNumber:   [this.lineCustomer.telephoneNumber,   [Validators.required]],
    customerId : [this.lineCustomer.customerId,   [Validators.required]],
    customerFirstName : [this.lineCustomer.customerFirstName,   [Validators.required]],
    customerLastName : [this.lineCustomer.customerLastName,   [Validators.required]],
    customerAddress : [this.lineCustomer.customerAddress,   [Validators.required]],
    customerEmail :  [this.lineCustomer.customerEmail,   [Validators.required]],  

    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLineCustomer(): LineCustomer {
    const lineCustomer = new LineCustomer();
    lineCustomer.telephoneNumber   = '0';
    lineCustomer.customerId = '';
    lineCustomer.customerFirstName = '';
    lineCustomer.customerLastName= '';
    lineCustomer.customerAddress = '';
    lineCustomer.customerEmail = '';


    return lineCustomer;
  }
  /**
   * Load from fields with the server model's values.
   */
  private loadForm(): void {
    this.form.patchValue({
      telephoneNumber: '0',
    
     customerId :  this.lineCustomer.customerId,
     customerFirstName : this.lineCustomer.customerFirstName,
     customerLastName :  this.lineCustomer.customerLastName,
     customerAddress :  this.lineCustomer.customerAddress,
     customerEmail :  this.lineCustomer.customerEmail,

      
    });
  }

  ngOnInit(): void {
    this.loadForm();
  }

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
  submit(): void {
    /* Validate inputs */
    if (this.form.invalid) {
      return Object.values(this.form.controls)
        .forEach(control => control.markAsTouched());
    }

    /* Get values */
    this.loadLineCustomerModel();
    console.log(this.lineCustomer);
    /* Call service */
  }

  /**
   * Load the LineCustomer model with form's values.
   */
  private loadLineCustomerModel(): void {
    let control;
    control = this.form.get('telephoneNumber');
    if ( control ) { this.lineCustomer.telephoneNumber = '0'; }
    control = this.form.get('customerId');
    if ( control ) { this.lineCustomer.customerId = control.value;}
    control = this.form.get('customerFirstName');
    if ( control ) { this.lineCustomer.customerFirstName = control.value;}
    control = this.form.get('customerLastName');
    if ( control ) { this.lineCustomer.customerLastName = control.value;}
    control = this.form.get('customerEmail');
    if ( control ) { this.lineCustomer.customerEmail = control.value;}
    control = this.form.get('customerAddress');
    if ( control ) { this.lineCustomer.customerAddress = control.value;}
  }

}
