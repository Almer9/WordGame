package com.example.storage;

import com.example.core.GameRoom;

import java.util.*;

public class RoomStorage {
    private final Set<GameRoom> storageSet = new HashSet<>() {
    };
    public boolean roomExists(long id){
        return storageSet.stream().anyMatch(x -> x.getId() == id);
    }
    public GameRoom getRoom(long id){
        return storageSet.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
    public GameRoom createRoom (String owner){
        GameRoom gameRoom = GameRoom.builder().id((long) storageSet.size())
                .isStarted(false)
                .isEnded(false)
                .owner(owner)
                .players(new ArrayList<>(List.of(owner)))
                .words(new ArrayList<>())
                .currPlayer(owner)
                .build();
        storageSet.add(gameRoom);
        return gameRoom;
    }
}
