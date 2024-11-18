import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;


  phone: string;
  password: string;
  retypePassword: string
  fullName: string;
  address: string;
  isAccepted: boolean;;
  dateOfBirth: Date;

  constructor(private http: HttpClient, private router: Router) {
    this.phone = '11332257';
    this.password = '123456';
    this.retypePassword = '123456';
    this.fullName = 'Nguyen Van A';
    this.address = 'ap 5';
    this.isAccepted = true;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);

    // Dependency Injection

  }
  onPhoneChange() {
    console.log(this.phone);
  }

  register() {
    const message = this.phone + ' ' + this.password + ' ' + this.retypePassword + ' ' + this.fullName + ' ' + this.dateOfBirth + ' ' + this.address + ' ' + this.isAccepted;
    // alert(message);
    debugger
    const apiUrl = 'http://localhost:9090/shopapp/api/v1/users/register';
    const registerData = {
      "fullname": this.fullName,
      "phone_number": this.phone,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dateOfBirth,
      "facebook_account_id": 0,
      "google_account_id": 0,
      "role_id": 1
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    this.http.post(apiUrl, registerData, {headers}).subscribe(
      {
        next: (response: any) => {
          debugger
          if(response && (response.status == 200 || response.status == 201)){
            this.router.navigate(['/login']);
          }else{
          
          }
        },
        complete: () => {
          alert('Register completed');
        },
        error: (error: any) => {
          debugger
          alert(`Can not register: ${error.error}`);
        }
      }
    );

  }

  checkPasswordsMatch() {
    if (this.password !== this.retypePassword) {
      this.registerForm.form.controls['retypePassword'].setErrors({ 'passWordMissMatch': true });
    } else {
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }
  checkAge() {
    if (this.dateOfBirth) {
      const today = new Date()
      const birthDate = new Date(this.dateOfBirth)
      let age = today.getFullYear() - birthDate.getFullYear()
      const monthDiff = today.getMonth() - birthDate.getMonth()
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--
      }
      if (age < 18) {
        this.registerForm.form.controls['dateOfBirth'].setErrors({ 'invalidAge': true })
      } else {
        this.registerForm.form.controls['dateOfBirth'].setErrors(null)
      }
    }
  }
}
