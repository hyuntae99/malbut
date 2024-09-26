package com.hyunn.malBut.repository;

import com.hyunn.malBut.entity.Pronunciation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PronunciationJpaRepository extends JpaRepository<Pronunciation, Long> {

  List<Pronunciation> findByLevel(String level);

}
