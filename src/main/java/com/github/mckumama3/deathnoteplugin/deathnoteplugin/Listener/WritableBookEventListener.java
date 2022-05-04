package com.github.mckumama3.deathnoteplugin.deathnoteplugin.Listener;

import com.github.mckumama3.deathnoteplugin.deathnoteplugin.Commands.GameStartCommandHandler;
import com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic.DeathNoteLogic;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;

public class WritableBookEventListener implements Listener
{
    @EventHandler
    public void OnEditBook(PlayerEditBookEvent e)
    {
        if(GameStartCommandHandler.l != null){
            Player p = e.getPlayer();
            BookMeta b = e.getNewBookMeta();
            if (DeathNoteLogic.IsStarted)
            {
                if (p == DeathNoteLogic.KillerPlayer)
                {
                    String raw = b.getPage(1);
                    if (raw.equals(""))
                    {
                        return;
                    }
                    if (raw.split(" ").length == 2)
                    {
                        String to = raw.split(" ")[0];
                        String reason = raw.split(" ")[1];
                        if (DeathNoteLogic.Usernames.contains(to))
                        {
                            int i = DeathNoteLogic.Usernames.indexOf(to);
                            if (DeathNoteLogic.IsCorrectReason(reason))
                            {
                                DeathNoteLogic.Kill(
                                        DeathNoteLogic.Players.stream().toList().get(i),
                                        DeathNoteLogic.SearchIndex(reason));
                                e.getNewBookMeta().setPage(1, "");
                                e.setSigning(false);
                            }
                            else
                            {
                                p.sendMessage(ChatColor.RED + "その方法では殺せないぞ");
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "誰だ？？^^");
                        }
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "書き方が違うぞ^^" + ChatColor.GREEN + "プレイヤー名 死因");
                    }
                }
            }
        }
    }
}
