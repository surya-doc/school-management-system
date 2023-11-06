import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../api-config';

@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService {

  constructor(private http: HttpClient) { }

  public validateEmail(data: FormData) {
    return this.http.post(`${environment.apiUrl}/auth/validate-email`,data);
  }

  public generateOtp(data: FormData) {
    return this.http.post(`${environment.apiUrl}/auth/generate-otp`,data);
  }

  public resetPaswword(data: FormData) {
    return this.http.post(`${environment.apiUrl}/auth/reset-password`,data);
  }

  public setUserId(userId: any)
  {
    localStorage.setItem('userId',userId);
  }

  public getUserId(){
    return localStorage.getItem('userId');
  }
}
