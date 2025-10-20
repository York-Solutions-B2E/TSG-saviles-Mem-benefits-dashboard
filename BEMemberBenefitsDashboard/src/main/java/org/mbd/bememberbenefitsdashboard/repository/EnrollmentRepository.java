package org.mbd.bememberbenefitsdashboard.repository;
import org.mbd.bememberbenefitsdashboard.entity.Enrollment;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    Optional<Enrollment> findByMemberAndActiveTrue(Member member);

}
