package com.yfmf.footlog.domain.member.repository;


import com.yfmf.footlog.domain.member.domain.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findByEmail(String email);
}
