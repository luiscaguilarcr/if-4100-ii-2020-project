import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Line } from '../../models/line.model';
import { LineService } from '../../services/line.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-line-add',
  templateUrl: './line-add.component.html',
  styleUrls: ['./line-add.component.scss']
})
export class LineAddComponent implements OnInit {

  form: FormGroup;
  title = 'Crear una nueva línea';
  line: Line;
  private list = ['ADSL', 'Básica', 'RDSI'];

  constructor( private formBuilder: FormBuilder, private lineService: LineService, private router: Router) {
    this. line = this.buildLine();
    this.form = this.createForm();
  }
  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
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
    line.type   = 0;
    line.status = 'A';
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
      console.log(response);
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Línea creada exitosamente',
        showConfirmButton: false,
        timer: 1500
      });
      this.router.navigateByUrl('/line/list');
     });
  }
  /**
   * Load the line model with form's values.
   */
  private loadLineModel(): void {
    let control;
    control = this.form.get('telephoneNumber');
    if ( control ) { this.line.telephoneNumber = control.value; } else { console.log('Telephone Number value not loaded.'); }
    control = this.form.get('type');
    if ( control ) { this.line.type = this.list.indexOf(control.value); } else { console.log('Line value not loaded.'); }
  }
}
