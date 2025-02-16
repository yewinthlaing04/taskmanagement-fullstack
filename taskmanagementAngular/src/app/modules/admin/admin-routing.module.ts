import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostTaskComponent } from './components/post-task/post-task.component';
import { UpdateTaskComponent } from './components/update-task/update-task.component';

const routes: Routes = [
  { path: "dashboard" , component: DashboardComponent},
  { path: 'task' , component: PostTaskComponent},
  { path : 'task/:id/edit' , component : UpdateTaskComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
