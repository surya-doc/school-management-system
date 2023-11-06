import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { environment } from 'src/app/api-config';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {
  private apiUrl = `${environment.apiUrl}/api/user`;
  constructor(private http: HttpClient) { }
  updateProfileImage(userId: number, data: FormData): Observable<any> {
    const url = `${this.apiUrl}/image/${userId}`    
    return this.http.put(url, data);
  }
}
