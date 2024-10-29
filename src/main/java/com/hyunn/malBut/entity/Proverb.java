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
@Table(name = "proverb")
@Getter
@ToString(exclude = "proverbId")
@NoArgsConstructor
public class Proverb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proverb_id")
    private Long proverbId;

    // 영어 속담
    @Column(name = "english")
    private String english;

    // 한국어 속담
    @Column(name = "korean")
    private String korean;

    // 질문
    @Column(name = "question")
    private String question;

}


