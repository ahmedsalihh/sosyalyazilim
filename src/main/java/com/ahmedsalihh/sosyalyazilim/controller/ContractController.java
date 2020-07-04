package com.ahmedsalihh.sosyalyazilim.controller;

import com.ahmedsalihh.sosyalyazilim.models.Contract;
import com.ahmedsalihh.sosyalyazilim.models.Player;
import com.ahmedsalihh.sosyalyazilim.models.Team;
import com.ahmedsalihh.sosyalyazilim.services.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
@Slf4j
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @GetMapping
    public ResponseEntity<List<Contract>> findAll(){
        return ResponseEntity.ok(contractService.findAll());
    }

    @PostMapping
    public ResponseEntity create( @RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.save(contract));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> update(@PathVariable Long id, @RequestBody Contract contract) {
        if (!contractService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(contractService.save(contract));
    }

    @GetMapping("/getActiveTeamPlayers")
    public ResponseEntity getActiveTeamPlayers(@RequestParam("year") int year, @RequestParam("teamId") Long teamId) {
        List<Player> players = contractService.getActiveTeamPlayers(year,teamId);
        return ResponseEntity.ok(players);
    }

    @GetMapping("/getPlayerTeams")
    public ResponseEntity getPlayerTeams(@RequestParam("playerId") Long playerId) {
        List<Team> teams = contractService.getPlayerTeams(playerId);
        return ResponseEntity.ok(teams);
    }
}
