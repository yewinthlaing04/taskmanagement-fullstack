import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: false,
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  signUpForm! : FormGroup;
  hidePassword = true ;

  constructor( private form : FormBuilder ,
    private authService : AuthService ,
    private router : Router){
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

    const password = this.signUpForm.get('password')?.value
    const confirmPassword = this.signUpForm.get('confirmPassword')?.value

    if ( password !== confirmPassword ) {
      alert("Password does not match")
      return ;
    }
    // if match

    this.authService.signUp( this.signUpForm.value).subscribe( (res) => {
      console.log(res)
      if ( res.id != null ) {
        alert("user successfully registered")
        this.router.navigateByUrl("/login");
      }else {
        alert ("Sign Up failed, Please Try Again")
      }
    })
  }

  passwordMatchValidator( form: AbstractControl ) {
    const password = form.get('password')?.value
    const confirmPassword = form.get('confirmPassword')?.value

    return password === confirmPassword ? null : { mismatch : true}
  }

}
