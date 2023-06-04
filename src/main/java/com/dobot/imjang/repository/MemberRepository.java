package com.dobot.imjang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.entities.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
