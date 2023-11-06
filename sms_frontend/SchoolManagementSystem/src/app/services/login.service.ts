import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';
import * as jwt_decode from 'jwt-decode';
import { environment } from '../api-config';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public loginStatusSubject = new Subject<boolean>();
  public logoutStatusSubject = new Subject<boolean>();
  constructor(private http: HttpClient) {}

  public generateToken(loginData: any) {
    return this.http.post(`${environment.apiUrl}/auth/login`, loginData);
  }

  public loginUser(token: any) {
    localStorage.setItem('token', token);
    // const decodedToken = jwt.decode(token);
    console.log(token);
    
    return true;
  }
  
  getRoleOfUser(){
    let tokenStr = localStorage.getItem('token');
    const token: any = jwt_decode.jwtDecode(tokenStr);
    console.log(token.role);
    
    return token.role;
  }

  public isLoggedIn() {
    let tokenStr = localStorage.getItem('token');
    
    if (tokenStr == undefined || tokenStr == '' || tokenStr == null) {
      return false;
    } else {
      return true;
    }
  }

  public logout() {
    localStorage.clear();
    return true;
  }

  public getToken() {
    return localStorage.getItem('token');
  }

  public setUser(email: any) {
    this.http.get(`${environment.apiUrl}/api/fetch-user/${email}`).subscribe((data:any)=>{
      localStorage.setItem('user_id', data.id);
    });
    
  }

  public getUser() {
    let userStr = localStorage.getItem('user');
    if (userStr !== null) {
      return JSON.parse(userStr);
    } else {
      this.logout();
      return null;
    }
  }

  public getUserRole() {
    return this.getRoleOfUser();
  }
}
