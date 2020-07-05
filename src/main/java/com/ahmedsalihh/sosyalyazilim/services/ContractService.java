package com.ahmedsalihh.sosyalyazilim.services;

import com.ahmedsalihh.sosyalyazilim.models.Contract;
import com.ahmedsalihh.sosyalyazilim.models.Player;
import com.ahmedsalihh.sosyalyazilim.models.Team;
import com.ahmedsalihh.sosyalyazilim.repositories.ContractRepository;
import com.ahmedsalihh.sosyalyazilim.repositories.PlayerRepository;
import com.ahmedsalihh.sosyalyazilim.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public Contract save(Contract contract, Long playerId, Long teamId) {
        Optional<Player> player = playerRepository.findById(playerId);
        Optional<Team> team = teamRepository.findById(teamId);

        int experience = player.get().getExperienceAsMonth();
        String currency = team.get().getCurrency();

        int transferFee = experience * 100000;
        int teamPercentage = transferFee * 10 / 100;
        int contractFee = transferFee + teamPercentage;

        contract.setContractFee(contractFee + " " + currency);

        contract.setPlayer(player.get());
        contract.setTeam(team.get());
        return contractRepository.save(contract);
    }

    public List<Player> getActiveTeamPlayers(int year, Long teamId) {
        List<Contract> contracts = contractRepository.findByTeamId(teamId);
        List<Player> players = contracts.stream()
                .filter(f -> year <= f.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()
                        && year >= f.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())
                .map(item -> item.getPlayer())
                .collect(Collectors.toList());
        return players;
    }

    public List<Team> getPlayerTeams(Long playerId) {
        List<Contract> contracts = contractRepository.findByPlayerId(playerId);
        List<Team> teams = contracts.stream()
                .filter(f -> f.getPlayer().getId() == playerId)
                .map(item -> item.getTeam())
                .collect(Collectors.toList());
        return teams;
    }
}
