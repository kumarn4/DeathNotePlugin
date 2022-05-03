package com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic;

import net.kyori.adventure.text.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

public class DeathNoteLogic
{
    Collection<? extends Player> Players;
    Player KillerPlayer;
    public DeathNoteLogic(Collection<? extends Player> players){
        Players = players;
    }

    public void ChooseKiller(){
        int i = new Random().nextInt(Players.size());
        KillerPlayer = Players.stream().toList().get(i);
        KillerPlayer.sendTitle(ChatColor.RED + "あなたはキラです。。。", "");
    }
}
