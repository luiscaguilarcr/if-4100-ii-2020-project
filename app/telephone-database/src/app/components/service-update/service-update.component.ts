
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Service } from '../../models/service.model';
import { ServiceService } from '../../services/service.service';

@Component({
  selector: 'app-service-update',
  templateUrl: './service-update.component.html'
})
  export class ServiceUpdateComponent implements OnInit {
    /* Variables */
    form: FormGroup;
    title = 'Modificar servicio';
    service: Service;
    /* Constructor */
    constructor( private formBuilder: FormBuilder, private serviceService: ServiceService ) {
      this.service = this.buildService();
      this.form = this.createForm();
    }



  createForm(): FormGroup {
    return this.formBuilder.group({
      serviceCode : [this.service.serviceCode, [Validators.required]],
      name        : [this.service.name,        [Validators.required]],
      description : [this.service.description, [Validators.required]],
      cost        : [this.service.cost,        [Validators.required]],
    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  
 private buildService(): Service {
   const service = new Service();
   service.serviceCode  = 0;
   service.name  = '';
   service.description = '';
   service.cost = 0;
   return service;
 }
  /**
   * Load from fields with the server model's values.
   */
  private loadForm(): void {
    this.form.patchValue({
      serviceCode: this.service.serviceCode,
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
  submit() {
    /* Validate inputs */
    if (this.form.invalid) {
      return Object.values(this.form.controls)
        .forEach(control => control.markAsTouched());
    }
    /* Get values */
    this.loadServiceModel();
    console.log(this.service);
    /* try add */
    this.serviceService.add(this.service);
  }

  /**
   * Load the line model with form's values.
   */
  private loadServiceModel(): void {
    let control;
    control = this.form.get('serviceCode');
    if ( control ) { this.service.serviceCode = control.value; }
    control = this.form.get('name');
    if ( control ) { this.service.name = control.value; }
    control = this.form.get('description');
    if ( control ) { this.service.description = control.value; }
    control = this.form.get('cost');
    if ( control ) { this.service.cost = control.value; }

  }
}
