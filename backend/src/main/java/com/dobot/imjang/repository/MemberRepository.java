package com.dobot.imjang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dobot.imjang.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
