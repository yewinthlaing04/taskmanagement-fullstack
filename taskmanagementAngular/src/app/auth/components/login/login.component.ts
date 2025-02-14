import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm! : FormGroup

  constructor(private form : FormBuilder ,
    private authService : AuthService ,
    private router : Router
  ){
    this.loginForm = form.group({
      email : [ '' , [ Validators.required , Validators.email]] ,
      password : ['' , [Validators.required]]
    })
  }


  onSubmit(){
    console.log(this.loginForm.value)
    this.authService.login( this.loginForm.value).subscribe( (res) => {
      console.log(res)
      if ( res.userId !== null ) {
        const user = {
          id : res.userId ,
          role: res.userRoles
        }
        StorageService.saveUser(user)
        StorageService.saveToken(res.jwt)
        if ( StorageService.isAdminLoggedIn()){
          this.router.navigateByUrl("/admin/dashboard")
        }else if ( StorageService.isEmployeeLoggedIn()){
          this.router.navigateByUrl("/employee/dashboard")
        }
        alert("User Login successfully " )
      }else {
        alert("Invalid Credentials")
      }
    })
  }
}
