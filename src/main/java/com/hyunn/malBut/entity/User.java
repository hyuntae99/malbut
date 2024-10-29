package com.hyunn.malBut.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@ToString(exclude = "userId")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    // 이메일
    @Column(name = "email")
    private String email;

    // 인증 코드
    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private boolean status;

    private User(String email) {
        this.email = email;
        this.code = null;
        this.status = false;
    }

    public static User createUser(String email) {
        return new User(email);
    }

    public void updateCode(String code) {
        this.code = code;
    }

    public void registerUser() {
        this.status = true;
    }

}