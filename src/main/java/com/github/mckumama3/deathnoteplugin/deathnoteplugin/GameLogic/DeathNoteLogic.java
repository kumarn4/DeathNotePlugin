package com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

public class DeathNoteLogic
{
    Collection<? extends Player> Players;
    public DeathNoteLogic(Collection<? extends Player> players){
        Players = players;
    }

    public Player ChooseKiller(){
        int i = new Random().nextInt(Players.size());
        return Players.stream().toList().get(i);
    }
}
