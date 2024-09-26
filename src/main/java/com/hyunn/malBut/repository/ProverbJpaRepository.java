package com.hyunn.malBut.repository;

import com.hyunn.malBut.entity.Proverb;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProverbJpaRepository extends JpaRepository<Proverb, Long> {

  // 랜덤으로 5개의 속담을 가져오는 쿼리
  @Query(value = "SELECT * FROM proverb ORDER BY RAND() LIMIT 5", nativeQuery = true)
  List<Proverb> findRandomProverbs();

}