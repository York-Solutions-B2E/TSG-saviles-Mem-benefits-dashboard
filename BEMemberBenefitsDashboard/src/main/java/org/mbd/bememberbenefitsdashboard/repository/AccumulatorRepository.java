package org.mbd.bememberbenefitsdashboard.repository;
import org.mbd.bememberbenefitsdashboard.entity.Accumulator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AccumulatorRepository extends JpaRepository<Accumulator, UUID> {
}
