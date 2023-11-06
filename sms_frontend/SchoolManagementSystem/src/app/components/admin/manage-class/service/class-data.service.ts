import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { environment } from 'src/app/api-config';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClassDataService {
  private apiUrl = `${environment.apiUrl}/api/class`;
  classOperationSuccess = new Subject<void>();
  constructor(private http: HttpClient) { }

  getAllclasses(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getClassesPageWise(pageNo: number): Observable<any> {
    const url = `${this.apiUrl}/page?pageno=${pageNo}`;
    return this.http.get(url);
  }

  getClass(classId: number): Observable<any> {
    const url = `${this.apiUrl}/download/${classId}`;
    return this.http.get(url);
  }

  getClassByClassTeacherId(teacherId: number):Observable<any>{
    const url = `${this.apiUrl}/byClassTeacher/${teacherId}`;
    return this.http.get(url);
  }

  createClass(classData: FormData): Observable<any> {
    const url = `${this.apiUrl}`
    // console.log(classData.get('file'), classData.get('heading'));
    
    return this.http.post(url, classData);
  }

  updateClass(updatedData: any): Observable<any> {
    const url = `${this.apiUrl}`;
    return this.http.put(url, updatedData);
  }

  deleteClass(classId: number): Observable<any> {
    const url = `${this.apiUrl}/${classId}`;
    return this.http.delete(url);
  }

  getTeachersByQualification(qualification: string): Observable<any> {
    const url = `${environment.apiUrl}/api/teacher/qualification/${qualification}`;
    return this.http.get(url);
  }

  assignClassTeacher(class_id: number, teacher_id: number){
    const value = {
      class_id,
      teacher_id
    }
    const url = `${this.apiUrl}/assign/teacher`;
    return this.http.put(url, value);
  }
}
