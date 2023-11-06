import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { environment } from 'src/app/api-config';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NoticeService {
  classOperationSuccess = new Subject<void>();
  private apiUrl = `${environment.apiUrl}/notices`;

  constructor(private http: HttpClient) { }

  getAllNotices(pageNo): Observable<any> {
    const url = `${this.apiUrl}?pageno=${pageNo}`;
    return this.http.get(url);
  }

  getActiveNotices(): Observable<any> {
    const url = `${environment.apiUrl}/notices/active`;
    return this.http.get(url);
  }

  getNotice(noticeId: number): Observable<any> {
    const url = `${this.apiUrl}/${noticeId}`;
    return this.http.get(url);
  }

  createNotice(noticeData: FormData): Observable<any> {
    const url = `${this.apiUrl}`
    console.log(noticeData.get('file'), noticeData.get('heading'));
    
    return this.http.post(url, noticeData);
  }

  updateNotice(noticeId: number, updatedData: any): Observable<any> {
    const url = `${this.apiUrl}/${noticeId}`;
    return this.http.put(url, updatedData);
  }

  deleteNotice(noticeId: number): Observable<any> {
    const url = `${this.apiUrl}/${noticeId}`;
    return this.http.delete(url);
  }

  selectedOptionSubject: Subject<string> = new Subject<string>();

  raiseSelectOption(option: string){
      this.selectedOptionSubject.next(option);
  }
}