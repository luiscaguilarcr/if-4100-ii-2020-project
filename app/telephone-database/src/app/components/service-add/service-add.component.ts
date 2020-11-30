import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Service } from '../../models/service.model';



@Component({
  selector: 'app-service-add',
  templateUrl: './service-add.component.html',
  styleUrls: ['./service-add.component.scss']
})
export class LineAddComponent implements OnInit {

  form: FormGroup;
  title = 'Crear una nueva línea';
  service: Service;

  constructor( private formBuilder: FormBuilder ) {
    this.service = this.buildLine();
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
      name :   [this.service.name,   [Validators.required]],
      descrition:  [this.service.descrition,   [Validators.required]],
      cost :  [this.service.cost,   [Validators.required]],
    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLine(): Service {
    const service = new Service();
    service.name  = '';
    service.descrition      = '';
    service.cost = '';
    service.customerLastName  = '';
    service.customerAddress   = '';
    service.customerEmail     = '';
    service.type              = '';
    return service;
  }
  /**
   * Load from fields with the server model's values.
   */
  private loadForm(): void {
    this.form.patchValue({
      telephoneNumber: this.service.telephoneNumber,
      customerId: this.service.customerId,
      customerFirstName: this.service.customerFirstName,
      customerLastName: this.service.customerLastName,
      customerAddress: this.service.customerAddress,
      customerEmail: this.service.customerEmail,
      type: this.service.type,
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
    this.loadServiceModel();
    console.log(this.service);
  }

  /**
   * Load the line model with form's values.
   */
  private loadLineModel(): void {
    let control;
    control = this.form.get('telephoneNumber');
    if ( control ) { this.service.telephoneNumber = control.value; }
    control = this.form.get('customerId');
    if ( control ) { this.service.customerId = control.value; }
    control = this.form.get('customerFirstName');
    if ( control ) { this.service.customerFirstName = control.value; }
    control = this.form.get('customerLastName');
    if ( control ) { this.service.customerLastName = control.value; }
    control = this.form.get('customerAddress');
    if ( control ) { this.service.customerAddress = control.value; }
    control = this.form.get('customerEmail');
    if ( control ) { this.service.customerEmail = control.value; }
    control = this.form.get('type');
    if ( control ) { this.service.type = control.value; }
  }

}
