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
      service_code :   [this.service.service_code,   [Validators.required]],
      name :   [this.service.name,   [Validators.required]],
      description:  [this.service.description,   [Validators.required]],
      cost :  [this.service.cost,   [Validators.required]],
    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLine(): Service {
    const service = new Service();
    service.service_code  = '';
    service.name  = '';
    service.description      = '';
    service.cost = '';
    return service;
  }
  /**
   * Load from fields with the server model's values.
   */
  private loadForm(): void {
    this.form.patchValue({
      service_code: this.service.service_code,
      name: this.service.name,
      description: this.service.description,
      cost: this.service.cost,
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
  private loadServiceModel(): void {
    let control;
    control = this.form.get('service_code');
    if ( control ) { this.service.service_code = control.value; }
    control = this.form.get('name');
    if ( control ) { this.service.name = control.value; }
    control = this.form.get('description');
    if ( control ) { this.service.description = control.value; }
    control = this.form.get('cost');
    if ( control ) { this.service.cost = control.value; }

  }

}
