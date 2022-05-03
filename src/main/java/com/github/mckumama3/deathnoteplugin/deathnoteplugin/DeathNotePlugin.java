package com.github.mckumama3.deathnoteplugin.deathnoteplugin;

import com.github.mckumama3.deathnoteplugin.deathnoteplugin.Commands.GameStartCommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DeathNotePlugin extends JavaPlugin  {

    @Override
    public void onEnable()
    {
        Objects.requireNonNull(getCommand("start")).setExecutor(new GameStartCommandHandler());
    }
}
