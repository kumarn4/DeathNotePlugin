package com.github.mckumama3.deathnoteplugin.deathnoteplugin;

import com.github.mckumama3.deathnoteplugin.deathnoteplugin.Commands.GameEndCommandHandler;
import com.github.mckumama3.deathnoteplugin.deathnoteplugin.Commands.GameStartCommandHandler;
import com.github.mckumama3.deathnoteplugin.deathnoteplugin.Listener.PlayerEventListener;
import com.github.mckumama3.deathnoteplugin.deathnoteplugin.Listener.WritableBookEventListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DeathNotePlugin extends JavaPlugin  {

    private static Plugin plugin;
    @Override
    public void onEnable()
    {
        plugin = this;
        getServer().getPluginManager().registerEvents(new WritableBookEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
        Objects.requireNonNull(getCommand("start")).setExecutor(new GameStartCommandHandler());
        Objects.requireNonNull(getCommand("end")).setExecutor(new GameEndCommandHandler());
    }
    public static Plugin GetPlugin(){
        return plugin;
    }
}
