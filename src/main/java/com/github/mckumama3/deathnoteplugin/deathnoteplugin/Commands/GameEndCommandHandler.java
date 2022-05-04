package com.github.mckumama3.deathnoteplugin.deathnoteplugin.Commands;

import com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic.DeathNoteLogic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GameEndCommandHandler implements CommandExecutor
{
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args)
    {
        if(GameStartCommandHandler.l != null)
        {
            DeathNoteLogic.EndGame();
            GameStartCommandHandler.l = null;
        }
        return true;
    }
}
