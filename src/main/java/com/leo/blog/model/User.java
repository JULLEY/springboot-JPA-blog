package com.leo.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> OBJECT -> 테이블로 매핑해주는 기술
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder    // 빌더패턴
@Entity // User 클래스가 MySql에 테이블 생성된다.
public class User {

    @Id // primary key 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 플젝에서 연결된 DB 넘버링 방식을 따라간다.
    private int id; // auto_increment , sequence

    @Column(nullable = false, length = 30)
    private String username; // 아이디
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 50)
    private String email;
    @ColumnDefault("'user'")
    private String role;    // admin, user, manager
    @CreationTimestamp  // 시간자동입력
    private Timestamp createDate;
}