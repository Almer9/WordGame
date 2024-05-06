package com.example.controller;

import com.example.core.GameRoom;
import com.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class GameController {
    @Autowired
    GameService gameService;
    @PostMapping("/createroom")
    public static ResponseEntity<?> createRoom(@RequestBody Map<String,String> json){
        try {
            return ResponseEntity.ok(GameService.createRoom(json.get("owner")));
        }
        catch (ClassCastException e){
            return ResponseEntity.unprocessableEntity().body("Passed wrong object type, try again");
        }
        catch (RuntimeException e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

    }
    @GetMapping("/getroom/{room}")
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
    @PostMapping("/addplayer/{room}")
    public static ResponseEntity<?> addPlayer(@PathVariable Long room, @RequestBody Map<String,String> json){
        GameRoom gameRoom;
        try {
            gameRoom = GameService.addPlayer(room, json.get("player"));
        }
        catch (ClassCastException e){
            return ResponseEntity.unprocessableEntity().body("Passed wrong object type, try again");
        }
        catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

        return ResponseEntity.ok(gameRoom);
    }
    @PostMapping("/maketurn/{room}")
    public static ResponseEntity<?> makeTurn(@PathVariable Long room, @RequestBody Map<String,String> json) {
        GameRoom gameRoom;
        try {
            gameRoom = GameService.makeTurn(room, json.get("player"), json.get("word"));
        }
        catch (ClassCastException e){
            return ResponseEntity.unprocessableEntity().body("Passed wrong object type, try again");
        }
        catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

        return ResponseEntity.ok(gameRoom);
    }
    @PostMapping("/rejectparticipation/{room}")
    public static ResponseEntity<?> rejectParticipation(@PathVariable Long room, @RequestBody Map<String,String> json){
        GameRoom gameRoom;
        try {
            gameRoom = GameService.rejectParticipation(room, json.get("player"));
        }
        catch (ClassCastException e){
            return ResponseEntity.unprocessableEntity().body("Passed wrong object type, try again");
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
