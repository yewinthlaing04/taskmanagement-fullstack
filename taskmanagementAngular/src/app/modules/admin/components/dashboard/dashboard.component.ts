import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {


  listOfTask : any = []

  constructor( private adminService : AdminService){
    this.getAllTasks()
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


}
