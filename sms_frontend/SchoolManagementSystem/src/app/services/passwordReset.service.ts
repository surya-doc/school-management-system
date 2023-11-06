import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from '../api-config';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class PasswordReset {
    private apiUrl = `${environment.apiUrl}/api/user/updatepassword`;
    constructor(private http: HttpClient) { }
    updatePasswordRequest(userId: number, passwordUpdateData: FormData): Observable<any> {
      const url = `${this.apiUrl}/${userId}`;
      return this.http.put(url, passwordUpdateData);
    }
  public checkValidation(email: String, password: String) {
    return this.http.post(`${environment.apiUrl}/auth/isvalid`, {email, password});
  }
}