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
@Table(name = "pronunciation")
@Getter
@ToString(exclude = "pronunciationId")
@NoArgsConstructor
public class Pronunciation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pronunciation_id")
    private Long pronunciationId;

    // 한국어 문장
    @Column(name = "korean")
    private String korean;

    // 난이도
    @Column(name = "level")
    private String level;

    // mp3 링크
    @Column(name = "link")
    private String link;

}