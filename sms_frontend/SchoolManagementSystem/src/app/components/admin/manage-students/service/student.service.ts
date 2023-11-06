import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { environment } from 'src/app/api-config';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  classOperationSuccess = new Subject<void>();
  private apiUrl = `${environment.apiUrl}/api/student`;

  constructor(private http: HttpClient) { }

  getAllStudents(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  getStudentsByPage(pageNo: number): Observable<any> {
    const url = `${this.apiUrl}/page?pageno=${pageNo}`;
    return this.http.get(url);
  }

  getStudent(studentId: number): Observable<any> {
    const url = `${this.apiUrl}/${studentId}`;
    return this.http.get(url);
  }

  createStudent(studentData: FormData): Observable<any> {
    // const url = `${this.apiUrl}/upload`
    console.log(studentData.get('file'), studentData.get('heading'));
    
    return this.http.post(this.apiUrl, studentData);
  }

  updateStudent(studentId: number, updatedData: any): Observable<any> {
    const url = `${this.apiUrl}`;
    return this.http.put(url, updatedData);
  }

  deleteStudent(studentId: number): Observable<any> {
    const url = `${this.apiUrl}/${studentId}`;
    return this.http.delete(url);
  }

  mapToNewDetails(oldDetail){
    let studentDetail = {
      id: oldDetail.id,
      fullName: oldDetail.fullName,
      dob: oldDetail.dob,
      email: oldDetail.email,
      phoneNumber: oldDetail.phoneNumber,
      qualification: oldDetail.qualification,
      hobbies: oldDetail.hobbies,
      gender: oldDetail.gender,
      current_address: oldDetail.current_Address,
      permanent_address: oldDetail.permanent_Address,
      profile_pic: oldDetail.profilePic,
      dob_certificate: oldDetail.dobCertificate
    }
    return studentDetail;
  }

  addHobby(id, hobby){
    const url = `${this.apiUrl}/hobby/${id}`;
    return this.http.put(url, hobby);
  }

  addDobCertificate(studentId: number, studentData: FormData): Observable<any> {
    const url = `${this.apiUrl}/dob/${studentId}`
    console.log(studentData.get('file'), studentData.get('heading'));
    
    return this.http.put(url, studentData);
  }

  getStudentsByClassId(clasId: number, pageNo: number){
    const url = `${this.apiUrl}/class/${clasId}?pageno=${pageNo}`;
    return this.http.get(url);
  }

  classAttendance(classId){
    const url = `${this.apiUrl}/byAttendance/${classId}`;
    return this.http.get(url);
  }

  getStudentByUserId(userId:any){
    const url = `${this.apiUrl}/byUserId/${userId}`;
    return this.http.get(url);
  }

  addStudentAttendance(student_id,data:Object){
    const url = `${environment.apiUrl}/attendance/${student_id}`;
    return this.http.post(url,data);
  }
}
