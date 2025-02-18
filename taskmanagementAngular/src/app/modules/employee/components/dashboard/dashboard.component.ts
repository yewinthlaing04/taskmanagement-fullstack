import { Component } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  listOfTasks : any = []

  constructor( private service : EmployeeService){
    this.getTasks();
  }

  getTasks(){
    this.service.getEmployeeTaskById().subscribe( (res) => {
      console.log(res)
      this.listOfTasks = res
    })
  }

  updateTask( id : number , status : string ) {
    this.service.updateStatus ( id , status ).subscribe( (res) => {
      if ( res.id != null ) {
        alert( "task status updated successfully" )
        this.getTasks()
      }else {
        alert("task status failed ")
      }
    })
  }
}
