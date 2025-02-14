import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from './auth/services/storage/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'taskmanagementAngular';

  isEmployeeLoggedIn : boolean = StorageService.isEmployeeLoggedIn()
  isAdminLoggedIn : boolean = StorageService.isAdminLoggedIn()

  constructor ( private router : Router ){

  }

  ngOnInit(): void {
    this.router.events.subscribe( event => {
      this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
      this.isEmployeeLoggedIn = StorageService.isEmployeeLoggedIn();
    })
  }

  logout(){
    StorageService.logout();
    this.router.navigateByUrl('/login');
  }
}


