import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Notice } from "src/app/services/models/notice.model";

@Injectable()
export class NoticeCRUD {

    constructor(private http: HttpClient) { }

    getImage() {
        this.http.get('http://localhost:8080/notices/123').subscribe(
            (data) => {
                // Handle the success response here.
                console.log(data);
                
            },
            (error) => {
                console.error('HTTP request failed:', error);
            }
        );
    }

    uploadImage(formData: Notice): void {
        
    }
}