package com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic;

import org.bukkit.entity.Player;

public class KillingPlayer
{
    public Player p;
    public int r;
    public KillingPlayer(Player target, int reasonnum)
    {
        p = target;
        r = reasonnum;
    }
}
