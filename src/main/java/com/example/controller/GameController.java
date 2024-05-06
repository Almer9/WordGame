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
    @PostMapping("/createroom/{owner}")
    public static ResponseEntity<?> createRoom(@PathVariable String owner){
       return ResponseEntity.ok(GameService.createRoom(owner));
    }
    @PostMapping("/getroom/{room}")
    public static ResponseEntity<?> getRoom(@PathVariable Long room){
        GameRoom gameRoom;
        try {
            gameRoom = GameService.getRoom(room);
        }
        catch (RuntimeException e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

        return ResponseEntity.ok(gameRoom);
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
    @PostMapping("/{room}/maketurn/{player}/{word}")
    public static ResponseEntity<?> makeTurn(@PathVariable Long room,@PathVariable String player, @PathVariable String word) {
        GameRoom gameRoom;
        try {
            gameRoom = GameService.makeTurn(room,player,word);
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
        if(gameRoom.isEnded()){
            return ResponseEntity.ok().body(String.format("Congratulations %s. You won!",gameRoom.getPlayers().get(0)));
        }
        return ResponseEntity.ok(gameRoom);
    }

}
