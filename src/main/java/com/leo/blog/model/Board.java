package com.leo.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob    // 대용량 데이터
    private String content; // 섬머노트 라이브러리 에디터

    @ColumnDefault("0")
    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER)  // Many = Board, One = User
    @JoinColumn(name="userId")
    private User user;  // FK

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)  // mappedBy 연관관계의 주인이 아니다(난 FK가 아니다. 컬럼생성 하지마라) > SELECT JOIN할때만 사용
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;
}