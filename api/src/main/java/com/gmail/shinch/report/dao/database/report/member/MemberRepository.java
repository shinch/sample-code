package com.gmail.shinch.report.dao.database.report.member;

import com.gmail.shinch.report.dao.database.BaseRepository;

import java.util.Optional;

public interface MemberRepository extends BaseRepository<MemberEntity, String> {
    Optional<MemberEntity> findByIdAndPassword(String id, String password);
    Optional<MemberEntity> findByIdAndToken(String id, String token);
    Optional<MemberEntity> findById(String id);
}
