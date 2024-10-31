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
@Table(name = "chat")
@Getter
@ToString(exclude = "chatId")
@NoArgsConstructor
public class Chat extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    // 유저 대화
    @Column(name = "user_input")
    private String userInput;

    private Chat( String userInput) {
        this.userInput = userInput;
    }

    public static Chat create(String userInput) {
        return new Chat(userInput);
    }
}
