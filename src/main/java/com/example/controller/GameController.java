package com.example.controller;

import com.example.core.GameRoom;
import com.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class GameController {
    @Autowired
    GameService gameService;
    @GetMapping("/createroom/{owner}")
    public static ResponseEntity<?> createRoom(@PathVariable String owner){
       return ResponseEntity.ok(GameService.createRoom(owner));
    }
    @PostMapping("/{room}/addplayer/{player}")
    public static ResponseEntity<?> addPlayer(@PathVariable Long room, @PathVariable String player){
        GameRoom gameRoom;
        try {
            gameRoom = GameService.addPlayer(room,player);
        }
        catch (RuntimeException e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

        return ResponseEntity.ok(gameRoom);
    }
    @PostMapping("/{room}/maketurn/{word}")
    public static ResponseEntity<?> makeTurn(@PathVariable Long room,@PathVariable String word) {
        GameRoom gameRoom;
        try {
            gameRoom = GameService.makeTurn(room, word);
        } catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

        return ResponseEntity.ok(gameRoom);
    }
    @PostMapping("/{room}/rejectparticipation/{player}")
    public static ResponseEntity<?> rejectParticipation(@PathVariable Long room,@PathVariable String player){
        GameRoom gameRoom;
        try {
            gameRoom = GameService.rejectParticipation(room,player);
        }
        catch (RuntimeException e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok(gameRoom);
    }

}
