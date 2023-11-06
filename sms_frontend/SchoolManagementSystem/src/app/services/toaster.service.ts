import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToasterService {
  successSubject = new Subject<string>();
  errorSubject = new Subject<string>();
}