package com.github.mckumama3.deathnoteplugin.deathnoteplugin.Listener;

import com.github.mckumama3.deathnoteplugin.deathnoteplugin.Commands.GameStartCommandHandler;
import com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic.DeathNoteLogic;
import com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic.DeathReason;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;

public class PlayerEventListener implements Listener
{
    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        if (DeathNoteLogic.IsStarted)
        {
            if (DeathNoteLogic.killp != null)
            {
               if (DeathNoteLogic.killp.p == p)
               {
                   if(DeathNoteLogic.killp.r == 0)
                   {
                       Location l = p.getLocation();
                       p.getWorld().getBlockAt(l).setType(Material.FIRE);
                   }
               }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        if (GameStartCommandHandler.l != null)
        {
            if (DeathNoteLogic.IsStarted)
            {
                DeathNoteLogic.killp = null;
                DeathNoteLogic.Surviver.remove(e.getPlayer());
                DeathNoteLogic.Usernames.remove(e.getPlayer().getName());

                if (DeathNoteLogic.Surviver.size() == 0)
                {
                    DeathNoteLogic.KillerWin();
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e)
    {
        if (GameStartCommandHandler.l != null)
            if (DeathNoteLogic.IsStarted)
                e.getPlayer().setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onSignEdit(SignChangeEvent e)
    {
        if (GameStartCommandHandler.l != null)
            if (DeathNoteLogic.IsStarted)
                if (Objects.requireNonNull(e.getLine(0)).equalsIgnoreCase("完成"))
                    DeathNoteLogic.SurviverWin();
    }
}
