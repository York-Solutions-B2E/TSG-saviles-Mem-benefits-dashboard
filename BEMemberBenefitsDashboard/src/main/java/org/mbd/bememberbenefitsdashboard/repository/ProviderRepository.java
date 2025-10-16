package org.mbd.bememberbenefitsdashboard.repository;

import org.mbd.bememberbenefitsdashboard.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
}
