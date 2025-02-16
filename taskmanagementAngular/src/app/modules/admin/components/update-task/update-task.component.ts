import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-task',
  standalone: false,
  templateUrl: './update-task.component.html',
  styleUrl: './update-task.component.css'
})
export class UpdateTaskComponent {

  id : number;
  updateTaskForm : FormGroup;
  listOfEmployee : any = [] ;
  listOfPriorities : any = [ "LOW" , "MEDIUM" , "HIGH"]

  constructor( private adminService : AdminService ,
    private router : ActivatedRoute ,
    private route : Router ,
    private form : FormBuilder
  ){
    this.id = this.router.snapshot.params['id']
    this.getTaskById()
    this.getUsers()
    this.updateTaskForm = form.group({
      employeeId : [ null , [Validators.required]],
      title : [ null , [Validators.required]],
      description: [null , [Validators.required]],
      dueDate: [null , [ Validators.required]],
      priority: [ null , [ Validators.required]]
    })
  }


  getTaskById(){
    this.adminService.getTaskById(this.id).subscribe ( (res) => {
      console.log(res)
      if ( res ) {
        this.updateTaskForm.patchValue( {
          title : res.title ,
          description : res.description ,
          dueDate : this.formatDate(res.dueDate),
          priority: res.priority ,
          employeeId : res.employeeId
        })
      }
    })
  }

  getUsers(){
    this.adminService.getUsers().subscribe( (res) => {
      console.log(res)
      this.listOfEmployee = res
    })
  }

  updateTask(){
    console.log(this.updateTaskForm.value)
    this.adminService.updateTask( this.id , this.updateTaskForm.value ).subscribe ( (res) => {
      if ( res.id != null ) {
        alert ( "Task updated successfully " );
        this.route.navigateByUrl("/admin/dashboard");
      }else {
        alert("something wrong with task updating")
      }
    })
  }

  formatDate(dateString : string | Date ) : string {
    if ( !dateString ) return '';
    const date = new Date(dateString)
    return date.toISOString().split('T')[0];
  }
}
