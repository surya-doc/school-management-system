import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { LoginService } from '../login.service';

@Injectable({ providedIn: 'root' })
export class UserGuard implements CanActivate {
  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (
      this.loginService.isLoggedIn() &&
      this.loginService.getUserRole() == 'student'
    ) {
      return true;
    }
    this.router.navigate([""]);
    return false;
  }
}