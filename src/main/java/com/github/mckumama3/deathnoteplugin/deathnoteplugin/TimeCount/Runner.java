package com.github.mckumama3.deathnoteplugin.deathnoteplugin.TimeCount;

import com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic.DeathNoteLogic;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.*;

public class Runner extends BukkitRunnable
{
    private int Sec;
    private final DeathNoteLogic logic;

    public Runner(int sec, DeathNoteLogic lg)
    {
        Sec = sec;
        logic = lg;
    }

    @Override
    public void run()
    {
        if (Sec < 0){
            logic.TimeLimitFinish();
            cancel();
            return;
        }
        logic.UpdateRemainTime(Sec);
        Sec--;
    }

}
