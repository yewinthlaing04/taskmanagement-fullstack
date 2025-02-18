import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-view-task-details',
  standalone: false,
  templateUrl: './view-task-details.component.html',
  styleUrl: './view-task-details.component.css'
})
export class ViewTaskDetailsComponent implements OnInit{

  taskId! : number ;
  taskData : any ;
  comments : any ;
  commentForm! : FormGroup;

  constructor( private service : AdminService ,
    private activatedRoute : ActivatedRoute ,
    private form : FormBuilder
  ){
    this.taskId = activatedRoute.snapshot.params["id"]
  }

  ngOnInit(): void {
      this.getTaskById();
      this.commentForm = this.form.group( {
        content: [null , Validators.required]
      })
  }

  getTaskById(){
    this.service.getTaskById( this.taskId ).subscribe ( (res) => {
      console.log( res)
      this.taskData = res
    })
  }

  getComments(){
    this.service.getCommentsByTask(this.taskId).subscribe( (res) => {
      console.log(res)
      this.comments = res
    })
  }

  addComment(){
    this.service.createComment(this.taskId , this.commentForm.get('content')?.value).subscribe( (res) => {
      if ( res.id != null ) {
        alert("comment posted successfully " )
      }else {
        alert("comment posted failed")
      }
    })
  }
}
