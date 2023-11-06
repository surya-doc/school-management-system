import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home.component';
import { ContactUsComponent } from './pages/contact-us/contact-us.component';
import { LoginComponent } from './pages/login/login.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ManageNoticesComponent } from './components/admin/manage-notices/manage-notices.component';
import { ManageTeachersComponent } from './components/admin/manage-teachers/manage-teachers.component';
import { ManageStudentsComponent } from './components/admin/manage-students/manage-students.component';
import { ManageClassComponent } from './components/admin/manage-class/manage-class.component';
import { ResetEmailComponent } from './pages/reset/reset-email/reset-email.component';
import { ResetPasswordComponent } from './pages/reset/reset-password/reset-password.component';
import { StudentAttendanceComponent } from './pages/attendance/student-attendance/student-attendance.component';
import { AttendanceRecordsComponent } from './pages/attendance-records/attendance-records.component';

import { TeacherUserGuard } from './services/guards/teacher-user.guard';
import { TeacherAdminGuard } from './services/guards/teacher-admin.guard';
import { TeacherAdminUserGuard } from './services/guards/teacher-admin-user.guard';
import { AdminGuard } from './services/guards/admin.guard';
import { TeacherGuard } from './services/guards/teacher.guard';


const routes: Routes = [
  {path:"",component: HomeComponent},
  {path:"contact-us",component: ContactUsComponent},
  {path:"login",component: LoginComponent},
  {path:"about-us", component:AboutUsComponent},
  {path:"profile", component:ProfileComponent,canActivate:[TeacherUserGuard]},
  {path:"manage-students", component:ManageStudentsComponent,canActivate:[TeacherAdminUserGuard]},
  {path:"manage-teachers", component:ManageTeachersComponent,canActivate:[TeacherAdminUserGuard]},
  {path:"manage-classes", component:ManageClassComponent,canActivate:[AdminGuard]},
  {path:"manage-notices", component:ManageNoticesComponent,canActivate:[AdminGuard]},
  {path:"reset-email", component:ResetEmailComponent},
  {path:"reset-password", component:ResetPasswordComponent},
  {path:"manage-attendance", component:StudentAttendanceComponent,canActivate:[TeacherGuard]},
  {path:"attendance-records", component:AttendanceRecordsComponent,canActivate:[TeacherAdminUserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
