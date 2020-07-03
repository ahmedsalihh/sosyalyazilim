package com.ahmedsalihh.sosyalyazilim.repositories;

import com.ahmedsalihh.sosyalyazilim.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long> {
}
