package org.mbd.bememberbenefitsdashboard.repository;
import org.mbd.bememberbenefitsdashboard.entity.Claim;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, UUID> {

    List<Claim> findTop5ByMemberOrderByReceivedDateDesc(Member member);

    List<Claim> findAllByMemberOrderByReceivedDateDesc(Member member);

}
