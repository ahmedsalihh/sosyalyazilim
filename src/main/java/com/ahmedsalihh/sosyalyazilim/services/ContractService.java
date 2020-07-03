package com.ahmedsalihh.sosyalyazilim.services;

import com.ahmedsalihh.sosyalyazilim.models.Contract;
import com.ahmedsalihh.sosyalyazilim.models.Player;
import com.ahmedsalihh.sosyalyazilim.repositories.ContractRepository;
import com.ahmedsalihh.sosyalyazilim.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public Optional<Contract> findById(Long id){
        return contractRepository.findById(id);
    }

    public Contract save(Contract contract){
        return contractRepository.save(contract);
    }

    public void deleteById(Long id){
        contractRepository.deleteById(id);
    }
}
