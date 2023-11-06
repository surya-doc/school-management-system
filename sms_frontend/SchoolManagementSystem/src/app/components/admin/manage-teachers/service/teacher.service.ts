import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { environment } from 'src/app/api-config';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {
  classOperationSuccess = new Subject<void>();
  private apiUrl = `${environment.apiUrl}/api/teacher`;

  constructor(private http: HttpClient) { }

  getAllTeachers(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getAllTeachersByPage(pageNo: number): Observable<any> {
    const url = `${this.apiUrl}/page?pageno=${pageNo}`;
    return this.http.get(url);
  }

  getTeacher(teacherId: number): Observable<any> {
    const url = `${this.apiUrl}/${teacherId}`;
    return this.http.get(url);
  }

  createTeacher(teacherData: FormData): Observable<any> {
    // const url = `${this.apiUrl}/upload`
    console.log(teacherData.get('file'), teacherData.get('heading'));
    return this.http.post(this.apiUrl, teacherData);
  }

  updateTeacher(teacherId: number, updatedData: any): Observable<any> {
    // const url = `${this.apiUrl}/${teacherId}`;
    return this.http.put(this.apiUrl, updatedData);
  }

  deleteTeacher(teacherId: number): Observable<any> {
    const url = `${this.apiUrl}/${teacherId}`;
    return this.http.delete(url);
  }

  getClassesByQualification(qualification: string): Observable<any> {
    const url = `${environment.apiUrl}/api/class/qualification/${qualification}`;
    return this.http.get(url);
  }

  getAllSubjects(): Observable<any> {
    const url = `${environment.apiUrl}/api/subject`;
    return this.http.get(url);
  }

  getTeacherByUserId(userId:any): Observable<any> {
    const url = `${environment.apiUrl}/api/teacher/byUserId/${userId}`;
    return this.http.get(url);
  }
}
