import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  searchForm! : FormGroup
  listOfTask : any = []

  constructor( private adminService : AdminService ,
    private form : FormBuilder
  ){
    this.getAllTasks()
    this.searchForm = form.group( {
      title : [ null ]
    })
  }

  getAllTasks(){
    this.adminService.getAllTask().subscribe( (res) => {
      this.listOfTask = res;
    })
  }

  deleteTask( id : number ) {
    this.adminService.deleteTask(id).subscribe( ( res ) => {
      alert ( "task deleted successfully ")
      this.getAllTasks()
    } )
  }

  searchTask(){
    this.listOfTask = []
    const title = this.searchForm.get('title')?.value
    console.log(title)
    this.adminService.searchTask(title).subscribe( (res) => {
      console.log(res)
      this.listOfTask = res 
    })
  }

}
