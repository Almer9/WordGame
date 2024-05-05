package com.example.service;

import com.example.core.GameRoom;
import com.example.storage.RoomStorage;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public static RoomStorage roomStorage = new RoomStorage();

    public static void changeCurrentPlayer(GameRoom gameRoom){
        int nextPlayer = gameRoom.getPlayers().indexOf(gameRoom.getCurrPlayer())+1;
        if(nextPlayer > gameRoom.getPlayers().size()-1){
            nextPlayer = 0;
        }
        gameRoom.setCurrPlayer(gameRoom.getPlayers().get(nextPlayer));
    }



    public static GameRoom createRoom(String owner){

        return roomStorage.createRoom(owner);
    }
    public static GameRoom addPlayer(Long gameRoomId,String player){
        GameRoom gameRoom = roomStorage.getRoom(gameRoomId);
        if(gameRoom.isStarted()){
            throw new RuntimeException("Game is already started, can't connect new player");
        }
        if(gameRoom.getPlayers().contains(player)){
            throw new RuntimeException(String.format("Player with nickname %s is already exists",player));
        }
        gameRoom.addPlayer(player);

        return gameRoom;
    }
    public static GameRoom makeTurn(Long gameRoomId,String word) throws RuntimeException {
        GameRoom gameRoom = roomStorage.getRoom(gameRoomId);
        if(gameRoom.isEnded()){
            throw new RuntimeException("This game is already ended");
        }
        if(gameRoom.getPlayers().size() == 1) {
            throw new RuntimeException("There is one player in the room (need at least two)");
        }
        if(!gameRoom.isStarted()){
            gameRoom.setStarted(true);
        }
        if(gameRoom.getWords().contains(word.toLowerCase())){
            throw new RuntimeException("This word was already used");
        }
        if(gameRoom.getWords().isEmpty()) {
            gameRoom.addWord(word);
        } else {
            if (!gameRoom.getWords().get(gameRoom.getWords().size() - 1).endsWith(word.toLowerCase().substring(0, 1))) {
                throw new RuntimeException(String.format("This word can't be accepted, current word is %s", gameRoom.getWords().get(gameRoom.getWords().size() - 1)));
            }
            gameRoom.addWord(word);
        }
        changeCurrentPlayer(gameRoom);
        return gameRoom;
    }
    public static GameRoom rejectParticipation(Long gameRoomId, String player){
        GameRoom gameRoom = roomStorage.getRoom(gameRoomId);
        if(!gameRoom.getCurrPlayer().equals(player)){
            throw new RuntimeException("It's not this player's turn right now, try again later");
        }
        gameRoom.getPlayers().remove(player);
        changeCurrentPlayer(gameRoom);
        if(gameRoom.getPlayers().size() == 1){
            gameRoom.setEnded(true);
            throw new RuntimeException(String.format("Congratulations %s. You won!",gameRoom.getPlayers().get(0)));
        }
        return gameRoom;
    }
}
