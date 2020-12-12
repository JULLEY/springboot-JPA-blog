package com.leo.blog.controller.api;

import com.leo.blog.dto.ResponseDto;
import com.leo.blog.model.RoleType;
import com.leo.blog.model.User;
import com.leo.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user){
      log.debug("UserApiController save 호출");
      System.out.println("UserApiController save 호출");
      user.setRole(RoleType.USER);
      userService.save(user);
      return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
        log.debug("UserApiController login 호출");
        System.out.println("UserApiController login 호출");
        User principal = userService.login(user);
        if(principal != null){
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
