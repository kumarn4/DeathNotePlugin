package com.github.mckumama3.deathnoteplugin.deathnoteplugin.GameLogic;

import com.github.mckumama3.deathnoteplugin.deathnoteplugin.Commands.GameStartCommandHandler;
import com.github.mckumama3.deathnoteplugin.deathnoteplugin.DeathNotePlugin;
import com.github.mckumama3.deathnoteplugin.deathnoteplugin.TimeCount.Runner;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class DeathNoteLogic
{
    public static boolean IsStarted = false;
    public static Collection<? extends Player> Players;
    public static ArrayList<Player> Surviver = new ArrayList<>();
    public static Player KillerPlayer;
    public static ArrayList<String> Usernames;
    public static KillingPlayer killp = null;
    public static int Sec;
    public static BossBar bossbar;
    public static Runner runner;

    public static ArrayList<DeathReason> _Reasons = new ArrayList<>();

    public DeathNoteLogic(Collection<? extends Player> players, int time)
    {
        IsStarted = true;
        Players = players;
        SetNameArray();
        _Reasons.add(new DeathReason(new String[]{"焼死", "焼かれて死ぬ"}));
        _Reasons.add(new DeathReason(new String[]{"クリーパー", "クリーパーに殺される"}));
        _Reasons.add(new DeathReason(new String[]{"毒殺", "毒に殺される"}));
        _Reasons.add(new DeathReason(new String[]{"落下死", "高いところから落ちる"}));
        _Reasons.add(new DeathReason(new String[]{"金床", "金床につぶされる"}));
        _Reasons.add(new DeathReason(new String[]{"落雷", "雷に打たれ死ぬ"}));
        Sec = time;
        runner = new Runner(Sec, this);
    }

    public void ChooseKiller()
    {
        int i = new Random().nextInt(Players.size());
        KillerPlayer = Players.stream().toList().get(i);
        KillerPlayer.sendTitle(
                "あなたは" + ChatColor.RED + "キラ" + ChatColor.WHITE + "です。。。",
                "本に" +
                        ChatColor.DARK_RED + "名前" +
                        ChatColor.WHITE + "と" +
                        ChatColor.DARK_RED + "死因" +
                        ChatColor.WHITE + "を書け");
        var io = new ItemStack(Material.WRITABLE_BOOK);
        var meta = io.getItemMeta();
        meta.setDisplayName("DeathNote");
        io.setItemMeta(meta);
        KillerPlayer.getInventory().setItem(
                9, io);
        for(int l = 0; l < Players.size(); l++){
            if(Players.stream().toList().get(l) != KillerPlayer ){
                Surviver.add(Players.stream().toList().get(l));
                Players.stream().toList().get(l).sendTitle(
                        "あなたは" + ChatColor.GREEN + "生存者" + ChatColor.WHITE + "です",
                        "生き残り、" + ChatColor.RED + "見つけ出せ"
                );
                Players.stream().toList().get(l).setScoreboard(KillerPlayer.getServer().getScoreboardManager().getNewScoreboard());
            }
        }
        ScoreboardManager sbm = KillerPlayer.getServer().getScoreboardManager();
        Scoreboard b = sbm.getNewScoreboard();
        Objective ob = b.registerNewObjective("res", "d");
        ob.setDisplaySlot(DisplaySlot.SIDEBAR);
        ob.setDisplayName("使用可能な死因");
        for (DeathReason d : _Reasons){
            Score s = ob.getScore(d.Words[0] + ", " + d.Words[1]);
            s.setScore(0);
        }
        KillerPlayer.setScoreboard(b);
        bossbar = KillerPlayer.getServer().createBossBar(
                "残り時間", BarColor.GREEN, BarStyle.SOLID, BarFlag.CREATE_FOG);
        bossbar.setVisible(true);
        for (Player p : Players){
            bossbar.addPlayer(p);
        }
        runner.runTaskTimer(DeathNotePlugin.GetPlugin(), 0, 20);
    }

    public void TimeLimitFinish(){
        for (Player p: Players)
        {
            p.sendTitle(
                    ChatColor.RED + "キラの勝利",
                    "キラは...." +
                            ChatColor.RED + KillerPlayer.getName());
        }
        EndGame();
        GameStartCommandHandler.l = null;
    }

    public void UpdateRemainTime(int Remainsec){
        int min = Remainsec / 60;
        int sec = Remainsec % 60;
        bossbar.setTitle("残り時間..." + min + "分" + sec + "秒");
        bossbar.setProgress((double) Remainsec / Sec);
    }

    private void SetNameArray()
    {
        Usernames = new ArrayList<>();
        for (int i = 0; i < Players.size(); i++)
        {
            Usernames.add(Players.stream().toList().get(i).getName());
        }
    }

    public static boolean IsCorrectReason(String reason)
    {
        for (DeathReason d : _Reasons)
        {
            for (String s : d.Words)
            {
                if (s.equalsIgnoreCase(reason))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static int SearchIndex(String r)
    {
        int i = 0;
        for (DeathReason d : _Reasons)
        {
            for (String ra : d.Words)
            {
                if (ra.equalsIgnoreCase(r))
                {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    public static void Kill(Player target, int ReasonNum)
    {
        switch (ReasonNum)
        {
            case 0 -> BurningKill(target);
            case 1 -> CreaperKill(target);
            case 2 -> PoisonKill(target);
            case 3 -> FallKill(target);
            case 4 -> AnvilKill(target);
            case 5 -> StrikeLightningKill(target);
        }
        KillerPlayer.getInventory().setItemInMainHand(new ItemStack(Material.WRITABLE_BOOK));
    }

    private static void BurningKill(Player p)
    {
        p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1f, 1f);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 18000, 3));
        p.setHealth(1);
        KillerPlayer.sendMessage(ChatColor.RED + p.getName() + "を焼き殺した");
        killp = new KillingPlayer(p, 0);
    }
    private static void CreaperKill(Player p)
    {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 18000, 100));
        p.setHealth(1);
        KillerPlayer.sendMessage(ChatColor.RED + p.getName() + "を爆殺した");
        for(int z = -1; z < 2; z++){
            for(int x = -1; x < 2; x++){
                Location l = new Location(
                        p.getWorld(),
                        p.getLocation().getBlockX() + x,
                        p.getLocation().getBlockY() + 3,
                        p.getLocation().getBlockZ() + z
                );
                p.getWorld().spawnEntity(l, EntityType.CREEPER);
            }
        }
    }
    private static void PoisonKill(Player p)
    {
        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 18000, 10));
        KillerPlayer.sendMessage(ChatColor.RED + p.getName() + "を毒殺した");
    }
    private static void FallKill(Player p)
    {
        Location l = p.getLocation();
        p.getLocation().set(l.getBlockX(), l.getBlockY() + 300, l.getBlockZ());
        KillerPlayer.sendMessage(ChatColor.RED + p.getName() + "空中に飛ばした");
    }
    private static void AnvilKill(Player p)
    {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 18000, 100));
        p.setHealth(1);
        KillerPlayer.sendMessage(ChatColor.RED + p.getName() + "金床で踏み殺した");
        Location l = p.getLocation();
        p.getWorld().getBlockAt(l.getBlockX(), l.getBlockY() + 10, l.getBlockZ()).setType(Material.ANVIL);
    }
    public static void StrikeLightningKill(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 18000, 100));
        p.setHealth(1);
        KillerPlayer.sendMessage(ChatColor.RED + p.getName() + "落雷で殺した");
        p.getWorld().strikeLightning(p.getLocation());
    }
    public static void KillerWin(){
        for (Player p: Players)
        {
            p.sendTitle(
                    ChatColor.RED + "キラの勝利",
                    "キラは...." +
                    ChatColor.RED + KillerPlayer.getName());
        }
        EndGame();
        GameStartCommandHandler.l = null;
    }

    public static void SurviverWin(){
        for (Player p: Players)
        {
            p.sendTitle(
                    ChatColor.GREEN + "生存者の勝利",
                    "キラは...." +
                            ChatColor.RED + KillerPlayer.getName());
        }
        EndGame();
        GameStartCommandHandler.l = null;
    }
    public static void EndGame(){
        for (Player p : Players)
        {
            for (PotionEffect e : p.getActivePotionEffects()){
                p.removePotionEffect(e.getType());
            }
            bossbar.removePlayer(p);
        }
        runner.cancel();
        runner = null;
        bossbar = null;
        KillerPlayer.setScoreboard(KillerPlayer.getServer().getScoreboardManager().getNewScoreboard());
        IsStarted = false;
        Players = null;
        KillerPlayer = null;
        Surviver = null;
        Usernames = null;
        killp = null;
    }
}
