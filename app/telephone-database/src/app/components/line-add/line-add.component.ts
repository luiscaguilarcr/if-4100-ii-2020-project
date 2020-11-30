import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Line } from '../../models/line.model';
import { LineService } from '../../services/line.service';

@Component({
  selector: 'app-line-add',
  templateUrl: './line-add.component.html',
  styleUrls: ['./line-add.component.scss']
})
export class LineAddComponent implements OnInit {

  form: FormGroup;
  title = 'Crear una nueva línea';
  line: Line;

  constructor( private formBuilder: FormBuilder, private lineService : LineService) {
    this. line = this.buildLine();
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
      telephoneNumber:   [this.line.telephoneNumber,   [Validators.required]],
      pointsQuantity:  [this.line.pointsQuantity,   [Validators.required]],
      type:  [this.line.type,   [Validators.required]],

    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLine(): Line {
    const line = new Line();
    line.telephoneNumber   = '';
    line.pointsQuantity   = '0';
    line.type   = '';


    return line;
  }
  /**
   * Load from fields with the server model's values.
   */
  private loadForm(): void {
    this.form.patchValue({
      telephoneNumber: this.line.telephoneNumber,
      pointsQuantity: this.line.pointsQuantity,
      type: this.line.type,

      
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
    this.loadLineModel();
    console.log(this.line);
    /* Call service */

    return this.lineService.addLine(this.line).then(response => console.log(response))
    .catch ((error: any) => {
      console.log(error);
    });
  }

  /**
   * Load the line model with form's values.
   */
  private loadLineModel(): void {
    let control;
    control = this.form.get('telephoneNumber');
    if ( control ) { this.line.telephoneNumber = control.value; }
    control = this.form.get('pointsQuantity');
    if ( control ) { this.line.pointsQuantity = control.value; }
    control = this.form.get('type');
    if ( control ) { this.line.type = control.value; }

  }

}
