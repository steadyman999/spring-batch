package com.steadyman.tuto.springbatch.repository;

import com.steadyman.tuto.springbatch.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
