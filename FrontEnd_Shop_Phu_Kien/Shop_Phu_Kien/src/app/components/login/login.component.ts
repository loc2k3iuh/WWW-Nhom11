
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from '../../dtos/user/login.dto';
import { Component, ViewChild } from '@angular/core';
import { UserService } from '../../service/user.service';
import { TokenService } from './../../service/token.service';
import { LoginResponse } from '../../responses/user/login.response';
import { RoleService } from 'src/app/service/role.service';
import { Role } from '../../models/role'; // Đường dẫn đến model Role



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phone: string;
  password: string;
  roles: Role[] = []; // Mảng roles
  rememberMe: boolean = true;
  selectedRole: Role | undefined; // Biến để lưu giá trị được chọn từ dropdown

  constructor(private router: Router,
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ) {
    this.phone = '0123456789';
    this.password = '123456';
  }


  ngOnInit() {
    this.roleService.getRoles().subscribe(
      {
        next: (roles: Role[]) => {
          debugger
          this.roles = roles;
          this.selectedRole = roles.length > 0 ? roles[0] : undefined;
        },
        
        error: (error: any) => {
          debugger
          alert(error?.error?.message);
        }
      }
    );

  }

  onPhoneChange() {
    console.log(this.phone);
  }


  login() {
    const message = `phone: ${this.phone}` +
      `password: ${this.password}`;
    //alert(message);
    debugger

    const loginDTO: LoginDTO = {
      phone_number: this.phone,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        debugger;
        const { token } = response;
        if (this.rememberMe) {
          this.tokenService.setToken(token);
        }                
        //this.router.navigate(['/login']);
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        alert(error.error.message);
      }
    });
  }
}
