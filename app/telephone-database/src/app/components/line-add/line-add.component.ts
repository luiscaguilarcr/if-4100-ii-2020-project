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
  title = 'Create new line';
  line: Line;

  constructor( private formBuilder: FormBuilder ) {
    this. line = this.buildLine();
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
      telephoneNumber: [this.line.telephoneNumber, [Validators.required]],
      customerId: [this.line.customerId, [Validators.required]],
      customerFirstName: [this.line.customerFirstName, [Validators.required]],
      customerLastName: [this.line.customerLastName, [Validators.required]],
      customerAddress: [this.line.customerAddress],
      customerEmail: [this.line.customerEmail, [Validators.required, Validators.email]],
      type: [this.line.type, [Validators.required]],
    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLine(): Line {
    const line = new Line();
    line.telephoneNumber = '';
    line.customerId = '';
    line.customerFirstName = '';
    line.customerLastName = '';
    line.customerAddress = '';
    line.customerEmail = '';
    line.type = '';
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
    // if (this.form !=null && this.form.get(input) != null){
    //   return this.form.get(input).invalid;// && this.form.get(input).touched;
    // }
    return false;
  }


  /**
   * Summit action.
   */
  submit(): void {
    console.log(this.form);
    if (this.form.valid){
      console.log('Is valid');
    } else {
      console.log('Invalid');
    }
    
  }


}

// [class.is-invalid]="invalidInput('telephoneNumber')"
// [class.is-invalid]="invalidInput('type')"
// [class.is-invalid]="invalidInput('customerId')"
// [class.is-invalid]="invalidInput('customerFirstName')"
// [class.is-invalid]="invalidInput('customerLastName')"
// [class.is-invalid]="invalidInput('customerEmail')"
// [class.is-invalid]="invalidInput('customerAddress')"