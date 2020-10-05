package com.rogit.matchorganizer.controller;

import com.rogit.matchorganizer.exception.ResourceNotFoundException;
import com.rogit.matchorganizer.model.Player;
import com.rogit.matchorganizer.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("players")
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping("players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable(value = "id") UUID playerId) throws ResourceNotFoundException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this id: " + playerId));
        return ResponseEntity.ok().body(player);
    }

    @PostMapping("players")
    public Player savePlayer(@RequestBody Player player) {
        return playerRepository.save(player);
    }

    @PutMapping("players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable(value = "id") UUID playerId,
                                                 @Valid @RequestBody Player playerDetails) throws ResourceNotFoundException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this id: " + playerId));

        player.setEmail(playerDetails.getEmail());
        player.setFirstName(playerDetails.getFirstName());
        player.setLastName(playerDetails.getLastName());
        player.setPassword(playerDetails.getPassword());

        return ResponseEntity.ok(playerRepository.save(player));
    }

    @DeleteMapping("players/{id}")
    public boolean deletePlayer(@PathVariable(value = "id") UUID playerId) throws ResourceNotFoundException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("Player not found for this id: " + playerId));
        playerRepository.deleteById(playerId);
        return true;
    }
}