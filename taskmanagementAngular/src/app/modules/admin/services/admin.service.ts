import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const BASE_URL = 'http://localhost:8080/'

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  getUsers() : Observable<any>{
    return this.http.get( BASE_URL + "api/admin/users" ,
      {
        headers: this.createAuthorizationHeader()
      }
    )
  }

  createTask(taskDto : any ) : Observable<any> {
    return this.http.post( BASE_URL + "api/admin/create-task" , taskDto , {
      headers : this.createAuthorizationHeader()
    })
  }

  getAllTask(): Observable<any>{
    return this.http.get( BASE_URL + "api/admin/tasks"  , {
      headers: this.createAuthorizationHeader()
    });
  }

  deleteTask( id : number ) : Observable<any> {
    return this.http.delete( BASE_URL + "api/admin/task/" + id , {
      headers : this.createAuthorizationHeader()
    });
  }

  getTaskById( id : number ) : Observable<any> {
    return this.http.get( BASE_URL + "api/admin/task/" + id  , {
      headers : this.createAuthorizationHeader()
    })
  }

  updateTask( id : number , taskDto : any ) : Observable<any> {
    return this.http.put( BASE_URL + `api/admin/task/${id}` , taskDto , {
      headers : this.createAuthorizationHeader()
    })
  }

  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization' , 'Bearer ' + StorageService.getToken()
    )
  }
}
