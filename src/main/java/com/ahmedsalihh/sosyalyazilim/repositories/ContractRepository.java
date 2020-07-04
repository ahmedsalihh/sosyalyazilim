package com.ahmedsalihh.sosyalyazilim.repositories;

import com.ahmedsalihh.sosyalyazilim.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByTeamId(Long id);
    List<Contract> findByPlayerId(Long id);
}
