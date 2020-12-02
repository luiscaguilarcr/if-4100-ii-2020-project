import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Service } from '../../models/service.model';
import { ServiceService } from '../../services/service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-service-add',
  templateUrl: './service-add.component.html',
  styleUrls: ['./service-add.component.scss']
})
export class ServiceAddComponent implements OnInit {
  /* Variables */
  form: FormGroup;
  title = 'Crear un nuevo servicio';
  service: Service;
  /* Constructor */
  constructor( private formBuilder: FormBuilder, private serviceService: ServiceService ) {
    this.service = this.buildService();
    this.form = this.createForm();
  }
  /**
   * Create Service form
   */
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
  submit(): any {
    /* Validate inputs */
    if (this.form.invalid) {
      return Object.values(this.form.controls)
        .forEach(control => control.markAsTouched());
    }
    /* Get values */
    this.loadServiceModel();
    console.log(this.service);
    this.service.status = 'A';
    /* try add */
    return this.serviceService.add(this.service).subscribe(response => {
      console.log(response);
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Servicio creado exitosamente',
        showConfirmButton: false,
        timer: 1500
      });
     });
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
