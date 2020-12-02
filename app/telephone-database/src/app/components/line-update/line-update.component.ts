import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Line } from '../../models/line.model';
import { LineService } from '../../services/line.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-line-update',
  templateUrl: './line-update.component.html',
  styleUrls: ['./line-update.component.scss']
})
export class LineUpdateComponent implements OnInit {
  form: FormGroup;
  title = 'Actualizar información de la línea';
  line: Line;
  private list = ['ADSL', 'Básica', 'RDSI'];

  constructor( private formBuilder: FormBuilder, private lineService: LineService, private router: Router) {
    this. line = this.buildLine();
    this.form = this.createForm();
  }
  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
      telephoneNumber: [this.line.telephoneNumber, [Validators.required]],
      type:            [this.line.type,            [Validators.required]],
      pointsQuantity:  [this.line.pointsQuantity,  [Validators.required]],
      status:          [this.line.status,          [Validators.required]],
    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLine(): Line {
    const line = new Line();
    line.telephoneNumber = -1;
    line.pointsQuantity  = 0;
    line.type            = -1;
    line.status          = 'A';
    return line;
  }
  /**
   * Load from fields with the server model's values.
   */
  private loadForm(): void {
    this.form.patchValue({
      telephoneNumber : this.line.telephoneNumber,
      type            : this.list[this.line.type],
      pointsQuantity  : this.line.pointsQuantity,
      status          : this.line.status === 'I' ? 'Inactiva' : 'Activa',
    });
  }
  ngOnInit(): void {
    this.line = this.lineService.getLineSelected();
    if (this.line.telephoneNumber === undefined){
      this.router.navigateByUrl('/line/list');
    }
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
    this.loadLineModel();
    console.log(this.line);
    console.log('Doing put request to /line');
    /* Do request */
    return this.lineService.update(this.line).subscribe(response => {
      console.log(response);
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Línea modificada exitosamente',
        showConfirmButton: false,
        timer: 1500
      });
    }, err => {
      console.log(err);
      if (err.status === 400 ){
        /* Bad request */
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: `Hubo un problema al agregar la línea. Código de error: ${err.headers.get('error_code') }, ${err.headers.get('error_message')}!`,
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
    if ( control ) { this.line.telephoneNumber = control.value; }
    control = this.form.get('type');
    if ( control ) { this.line.type = this.list.indexOf(control.value); }
    control = this.form.get('pointsQuantity');
    if ( control ) { this.line.pointsQuantity = control.value; }
    control = this.form.get('status');
    if ( control ) { this.line.status = control.value === 'Inactiva' ? 'I' : 'A'; }
  }
}
