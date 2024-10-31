package com.hyunn.malBut.repository;

import com.hyunn.malBut.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
