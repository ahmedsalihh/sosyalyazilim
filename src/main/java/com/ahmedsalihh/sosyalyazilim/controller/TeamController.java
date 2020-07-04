package com.ahmedsalihh.sosyalyazilim.controller;

import com.ahmedsalihh.sosyalyazilim.models.Team;
import com.ahmedsalihh.sosyalyazilim.services.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
@Slf4j
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> findAll(){
        return ResponseEntity.ok(teamService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> findById(@PathVariable Long id) {
        Optional<Team> team = teamService.findById(id);
        if (!team.isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(team.get());
    }

    @PostMapping
    public ResponseEntity create( @RequestBody Team team) {
        return ResponseEntity.ok(teamService.save(team));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> update(@PathVariable Long id, @RequestBody Team team) {
        if (!teamService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(teamService.save(team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!teamService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        teamService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
