import { Component, OnInit } from '@angular/core';
import { debounceTime } from 'rxjs';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit{
  collapsed: boolean =true;
  dropdown: boolean = false;
  role: String;
  LoggedIn: boolean = false;
  constructor(private loginService:LoginService,private router:Router){
    
    this.loginService.loginStatusSubject.subscribe(
      (data:any)=>{
        this.LoggedIn = true;
        this.role = this.loginService.getRoleOfUser();
        console.log(this.role, localStorage.getItem('role'));
        
      }
    )
  }

  ngOnInit(): void {
    this.role = this.loginService.getUserRole();
    console.log(this.role);
    
    this.LoggedIn = this.loginService.isLoggedIn();
    console.log(this.LoggedIn);
    
  }

  toggleCollapsed(): void {
    console.log("button clicked");
    this.collapsed = !this.collapsed;
  }

  toggleDropDown(): void {
    this.dropdown = !this.dropdown;
    console.log(this.dropdown);
    
  }

  logout():void {
    localStorage.clear();
    console.log("logout successful");
    // this.loginService.logoutStatusSubject.next(true);
    this.LoggedIn = false;
    this.router.navigate([""]);
  }
}
