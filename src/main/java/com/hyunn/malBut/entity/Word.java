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
@Table(name = "word")
@Getter
@ToString(exclude = "wordId")
@NoArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long wordId;

    // 영어 단어
    @Column(name = "english")
    private String english;

    // 한국어 단어
    @Column(name = "korean")
    private String korean;

    // 난이도
    @Column(name = "level")
    private boolean level;

}

