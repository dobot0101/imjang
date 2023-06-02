package com.dobot.imjang.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.entity.MemberKakaoLogin;

public interface MemberKakaoLoginRepository extends JpaRepository<MemberKakaoLogin, String> {

  Optional<MemberKakaoLogin> findByKakaoUserId(String id);

}
