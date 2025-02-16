import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-task',
  standalone: false,
  templateUrl: './post-task.component.html',
  styleUrl: './post-task.component.css'
})
export class PostTaskComponent {


  taskForm : FormGroup ;
  listOfEmployees : any = [] ;
  listOfPriorities : any = [ "LOW" , "MEDIUM" , "HIGH"]

  constructor(private adminService : AdminService ,
    private form : FormBuilder ,
    private router : Router
  ){
    this.getUsers()
    this.taskForm = form.group({
      employeeId : [ null , [Validators.required]],
      title : [ null , [Validators.required]],
      description: [null , [Validators.required]],
      dueDate: [null , [ Validators.required]],
      priority: [ null , [ Validators.required]]
    })
  }

  getUsers(){
    this.adminService.getUsers().subscribe( (res) => {
      this.listOfEmployees = res;
      console.log(res);
    })
  }

  postTask(){
    // console.log("helloworld")
    console.log(this.taskForm.value);
    this.adminService.createTask(this.taskForm.value).subscribe( (res) => {
      if ( res.id != null ) {
        alert("task has been created successfully")
        this.router.navigateByUrl("/admin/dashboard")

      }else {
        alert ( "something wrong with creating task, Please try again")
        return;
      }
    })
  }
}
