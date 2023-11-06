package com.project.SchoolManagementSystem.auth.Service;

import com.project.SchoolManagementSystem.users.Dto.UserLoginDto;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserServiceImpl userService;

    @Autowired
    public CustomUserDetailService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginDto user = this.userService.getUserDetailsByEmail(username).getBody();
        if(user != null)
        {
            return new User(user.getUsername(),user.getPassword(),user.getAuthorities());
        }
        else{
            throw new UsernameNotFoundException("User not found with username:"+username);
        }
    }
}
