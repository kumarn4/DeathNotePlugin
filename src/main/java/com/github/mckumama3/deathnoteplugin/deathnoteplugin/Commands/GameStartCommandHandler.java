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
            CommandSender sender,
            Command command,
            String label,
            String[] args)
    {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if(l == null)
            {
                l = new DeathNoteLogic(sender.getServer().getOnlinePlayers());
            }
        }

        return false;
    }
}
