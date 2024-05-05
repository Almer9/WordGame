package com.example.core;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameRoom {
 private Long id;
 private boolean isStarted;
 private boolean isEnded;
 private List<String> words = new ArrayList<>();
 private List<String> players = new ArrayList<>();
 private String owner;
 private String currPlayer;

 public void addPlayer(String player) {
     players.add(player);
 }
 public void addWord(String word){
    this.words.add(word.toLowerCase());
 }

}
