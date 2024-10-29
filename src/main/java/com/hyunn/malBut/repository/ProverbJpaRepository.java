package com.hyunn.malBut.repository;

import com.hyunn.malBut.entity.Proverb;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProverbJpaRepository extends JpaRepository<Proverb, Long> {

    // 랜덤으로 5개의 속담을 가져오는 쿼리
    @Query(value = "SELECT * FROM proverb ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Proverb> findRandomProverbs();

    // 난이도 0 (쉬운 문제) 중에서 랜덤으로 필요한 수만큼 가져오기
    @Query(value = "SELECT * FROM proverb WHERE level = false ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Proverb> findRandomEasyProverbs(@Param("count") int count);

    // 난이도 1 (어려운 문제) 중에서 랜덤으로 필요한 수만큼 가져오기
    @Query(value = "SELECT * FROM proverb WHERE level = true ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Proverb> findRandomHardProverbs(@Param("count") int count);


}