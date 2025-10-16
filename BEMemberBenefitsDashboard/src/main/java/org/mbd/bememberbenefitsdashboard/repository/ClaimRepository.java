package org.mbd.bememberbenefitsdashboard.repository;

import org.mbd.bememberbenefitsdashboard.entity.Claim;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClaimRepository extends JpaRepository<Claim, UUID> {

    List<Claim> findTop5ByMemberOrderByReceivedDateDesc(Member member);
}
