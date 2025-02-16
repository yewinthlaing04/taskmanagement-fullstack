import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostTaskComponent } from './components/post-task/post-task.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UpdateTaskComponent } from './components/update-task/update-task.component';


@NgModule({
  declarations: [
    DashboardComponent,
    PostTaskComponent,
    UpdateTaskComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AdminModule { }
