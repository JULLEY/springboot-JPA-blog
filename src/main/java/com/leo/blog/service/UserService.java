package com.leo.blog.service;

import com.leo.blog.model.RoleType;
import com.leo.blog.model.User;
import com.leo.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 스픨ㅇ이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void save(User user){
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void update(User user){
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고 영속화된 User 오브젝트를 수정
        // SELECT를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기위함
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update를 날려주기때문
        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패!!");
        });
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());

        // 회원수정 함수 종료시 서비스종료 = 트랜잭션 종료 = commit 이 자동으로 실행
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌
    }

    /*
    @Transactional(readOnly = true) // SELECT 할 때 트랜잭션 시작, 서비스 종료될때 트랜잭션 종료 (정합성 유지)
    public User login(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
    */
}
