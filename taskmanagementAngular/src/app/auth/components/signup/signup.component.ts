import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  standalone: false,
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  signUpForm! : FormGroup;
  hidePassword = true ;

  constructor( private form : FormBuilder){
    this.signUpForm = this.form.group( {
      name : [ null , [Validators.required ]],
      email : [ null , [Validators.required , Validators.email ]],
      password : [ null , [Validators.required ]],
      confirmPassword : [ null , [Validators.required]]
    } , {
      validators : this.passwordMatchValidator
    })
  }

  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword
  }

  onSubmit(){
    console.log( this.signUpForm.value);
  }

  passwordMatchValidator( form: AbstractControl ) {
    const password = form.get('password')?.value
    const confirmPassword = form.get('confirmPassword')?.value

    return password === confirmPassword ? null : { mismatch : true}
  }

}
