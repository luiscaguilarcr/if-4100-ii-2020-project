import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Call } from '../../models/call.model';
import { CallService } from '../../services/call.service';

@Component({
  selector: 'app-call-update',
  templateUrl: './call-update.component.html',
  //styleUrls: ['./call-update.component.scss']
})
export class CallUpdateComponent implements OnInit {

  form: FormGroup;
  title = 'Actualizar registro de llamada';
  call: Call;

  constructor( private formBuilder: FormBuilder, private callService: CallService) {
    this. call = this.buildLine();
    this.form = this.createForm();
  }
  createForm(): FormGroup {
    /* Create form */
    return this.formBuilder.group({
      telephoneNumber:   [this.call.telephoneNumber,   [Validators.required]],
      type:  [this.call.destinationTelephoneNumber,   [Validators.required]],
      startDate:  [this.call.startDate,   [Validators.required]],
      endDate:  [this.call.endDate,   [Validators.required]],
     
    });
  }
  /**
   * Initilize the model to prevent form errors.
   */
  private buildLine(): Call {
    const call = new Call();
    call.noCall=-1;
    call.telephoneNumber = -1;
    call.destinationTelephoneNumber  = 0;
    call.startDate           = '';
    call.endDate         = '';
    return call;
  }
  /**
   * Load from fields with the server model's values.
   */
  private loadForm(): void {
    this.form.patchValue({
      noCall: this.call.noCall,
      telephoneNumber: this.call.telephoneNumber,
      destinationTelephoneNumber: this.call.destinationTelephoneNumber,
      startDate: this.call.startDate,
      endDate: this.call.endDate,

    
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
    this.loadcallModel();
    this.call.noCall=-1;
    this.call.telephoneNumber = -1;
    this.call.destinationTelephoneNumber = -1;
    this.call.startDate = '';
    this.call.endDate = '';
    console.log(this.call);
    console.log('Doing post request to /call');
    /* Do request */
    return this.callService.add(this.call).subscribe(response => console.log(response));
  }
  /**
   * Load the line model with form's values.
   */
  private loadcallModel(): void {
    let control;
    control = this.form.get('noCall');
    if ( control ) { this.call.noCall = control.value; }
    control = this.form.get('telephoneNumber');
    if ( control ) { this.call.telephoneNumber = control.value; }
    control = this.form.get('destinationTelephoneNumber');
    if ( control ) { this.call.destinationTelephoneNumber = control.value; }
    control = this.form.get('startDate');
    if ( control ) { this.call.startDate = control.value; }
    control = this.form.get('endDate');
    if ( control ) { this.call.endDate = control.value; }

  }
}
