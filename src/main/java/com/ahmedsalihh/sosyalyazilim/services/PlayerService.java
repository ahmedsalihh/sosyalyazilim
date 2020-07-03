package com.ahmedsalihh.sosyalyazilim.services;

import com.ahmedsalihh.sosyalyazilim.models.Player;
import com.ahmedsalihh.sosyalyazilim.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(Long id){
        return playerRepository.findById(id);
    }

    public Player save(Player player){
        return playerRepository.save(player);
    }

    public void deleteById(Long id){
        playerRepository.deleteById(id);
    }
}
