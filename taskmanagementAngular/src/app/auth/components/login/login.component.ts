import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm! : FormGroup

  constructor(private form : FormBuilder){
    this.loginForm = form.group({
      email : [ '' , [ Validators.required , Validators.email]] ,
      password : ['' , [Validators.required]]
    })
  }


  onSubmit(){
    console.log(this.loginForm.value)
  }
}
