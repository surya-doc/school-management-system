import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DragScrollModule } from 'ngx-drag-scroll';
import { ReactiveFormsModule } from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { HomeComponent } from './pages/home/home.component';
import { NavComponent } from './components/nav/nav.component';
import { FooterComponent } from './components/footer/footer.component';
import { GalleryComponent } from './pages/home/gallery/gallery.component';
import { ClassScrollerComponent } from './pages/home/class-scroller/class-scroller.component';
import { NoticeBoardComponent } from './pages/home/notice-board/notice-board.component';
import { ContactUsComponent } from './pages/contact-us/contact-us.component';
import { LoginComponent } from './pages/login/login.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AdminComponent } from './components/admin/admin.component';
import { ManageNoticesComponent } from './components/admin/manage-notices/manage-notices.component';
import { ManageTeachersComponent } from './components/admin/manage-teachers/manage-teachers.component';
import { ManageStudentsComponent } from './components/admin/manage-students/manage-students.component';
import { ManageClassComponent } from './components/admin/manage-class/manage-class.component';
import { NoticeCardComponent } from './components/admin/manage-notices/notice-card/notice-card.component';
import { NoticeDetailComponent } from './components/admin/manage-notices/notice-detail/notice-detail.component';
import { CreateNoticeComponent } from './components/admin/manage-notices/create-notice/create-notice.component';
import { DropdownComponent } from './components/admin/dropdown/dropdown.component';
import { ClassCardComponent } from './components/admin/manage-class/class-card/class-card.component';
import { ClassCreateUpdateComponent } from './components/admin/manage-class/class-create-update/class-create-update.component';
import { StudentCardComponent } from './components/admin/manage-students/student-card/student-card.component';
import { StudentDetailsComponent } from './components/admin/manage-students/student-details/student-details.component';
import { StudentCreateUpdateComponent } from './components/admin/manage-students/student-create-update/student-create-update.component';
import { PhonenoFormatterPipe } from './services/pipe/phoneno-formatter.pipe';
import { PhoneFormatterDirective } from './services/directives/phone-formatter.directive';
import { TeacherCardComponent } from './components/admin/manage-teachers/teacher-card/teacher-card.component';
import { TeacherCreateUpdateComponent } from './components/admin/manage-teachers/teacher-create-update/teacher-create-update.component';
import { TeacherDetailsComponent } from './components/admin/manage-teachers/teacher-details/teacher-details.component';
import { ResetEmailComponent } from './pages/reset/reset-email/reset-email.component';
import { ResetPasswordComponent } from './pages/reset/reset-password/reset-password.component';
import { StudentAttendanceComponent } from './pages/attendance/student-attendance/student-attendance.component';
import { AttendanceCardComponent } from './pages/attendance/attendance-card/attendance-card.component';
import { AttendanceRecordsComponent } from './pages/attendance-records/attendance-records.component';
import { JwtInterceptor } from './services/jwt-interceptor';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavComponent,
    FooterComponent,
    GalleryComponent,
    ClassScrollerComponent,
    NoticeBoardComponent,
    ContactUsComponent,
    LoginComponent,
    AboutUsComponent,
    ProfileComponent,
    AdminComponent,
    ManageNoticesComponent,
    ManageTeachersComponent,
    ManageStudentsComponent,
    ManageClassComponent,
    NoticeCardComponent,
    NoticeDetailComponent,
    CreateNoticeComponent,
    DropdownComponent,
    ClassCardComponent,
    ClassCreateUpdateComponent,
    StudentCardComponent,
    StudentDetailsComponent,
    StudentCreateUpdateComponent,
    PhonenoFormatterPipe,
    PhoneFormatterDirective,
    TeacherCardComponent,
    TeacherCreateUpdateComponent,
    TeacherDetailsComponent,
    ResetEmailComponent,
    ResetPasswordComponent,
    StudentAttendanceComponent,
    AttendanceCardComponent,
    AttendanceRecordsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    DragScrollModule,
    MatIconModule,
    MatDialogModule,
    MatButtonModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
