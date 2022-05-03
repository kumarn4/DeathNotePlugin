package com.github.mckumama3.deathnoteplugin.deathnoteplugin.Commands;

import com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic.DeathNoteLogic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameStartCommandHandler implements CommandExecutor
{
    DeathNoteLogic l = null;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args)
    {
        if (sender instanceof Player player)
        {
            if(l == null)
            {
                l = new DeathNoteLogic(player.getServer().getOnlinePlayers());
            }
        }

        return false;
    }
}
