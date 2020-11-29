import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Line } from '../../models/line.model';

@Component({
  selector: 'app-line-add',
  templateUrl: './line-add.component.html',
  styleUrls: ['./line-add.component.scss']
})
export class LineAddComponent implements OnInit {

  form: FormGroup;
  title = 'Crear una nueva línea';
  line: Line;

  constructor( private formBuilder: FormBuilder ) {
    this. line = this.buildLine();
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
      telephoneNumber:   [this.line.telephoneNumber,   [Validators.required]],
      customerId:        [this.line.customerId,        [Validators.required]],
      customerFirstName: [this.line.customerFirstName, [Validators.required]],
      customerLastName:  [this.line.customerLastName,  [Validators.required]],
      customerAddress:   [this.line.customerAddress],
      customerEmail:     [this.line.customerEmail,     [Validators.required, Validators.email]],
      type:              [this.line.type,              [Validators.required]],
    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLine(): Line {
    const line = new Line();
    line.telephoneNumber   = '';
    line.customerId        = '';
    line.customerFirstName = '';
    line.customerLastName  = '';
    line.customerAddress   = '';
    line.customerEmail     = '';
    line.type              = '';
    return line;
  }
  /**
   * Load from fields with the server model's values.
   */
  private loadForm(): void {
    this.form.patchValue({
      telephoneNumber: this.line.telephoneNumber,
      customerId: this.line.customerId,
      customerFirstName: this.line.customerFirstName,
      customerLastName: this.line.customerLastName,
      customerAddress: this.line.customerAddress,
      customerEmail: this.line.customerEmail,
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
    this.dataService.addLine(this.line);
  }

  /**
   * Load the line model with form's values.
   */
  private loadLineModel(): void {
    let control;
    control = this.form.get('telephoneNumber');
    if ( control ) { this.line.telephoneNumber = control.value; }
    control = this.form.get('customerId');
    if ( control ) { this.line.customerId = control.value; }
    control = this.form.get('customerFirstName');
    if ( control ) { this.line.customerFirstName = control.value; }
    control = this.form.get('customerLastName');
    if ( control ) { this.line.customerLastName = control.value; }
    control = this.form.get('customerAddress');
    if ( control ) { this.line.customerAddress = control.value; }
    control = this.form.get('customerEmail');
    if ( control ) { this.line.customerEmail = control.value; }
    control = this.form.get('type');
    if ( control ) { this.line.type = control.value; }
  }

}
