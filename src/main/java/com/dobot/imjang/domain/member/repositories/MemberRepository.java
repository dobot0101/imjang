package com.dobot.imjang.domain.member.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.domain.member.entities.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
  Optional<Member> findByEmail(String email);
}
