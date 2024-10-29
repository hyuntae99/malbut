package com.hyunn.malBut.repository;

import com.hyunn.malBut.entity.Word;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WordJpaRepository extends JpaRepository<Word, Long> {

    // 난이도 0 (쉬운 문제) 중에서 랜덤으로 필요한 수만큼 가져오기
    @Query(value = "SELECT * FROM word WHERE level = false ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Word> findRandomEasyWords(@Param("count") int count);

    // 난이도 1 (어려운 문제) 중에서 랜덤으로 필요한 수만큼 가져오기
    @Query(value = "SELECT * FROM word WHERE level = true ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Word> findRandomHardWords(@Param("count") int count);

}
