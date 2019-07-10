package org.runnerer.reports;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.runnerer.reports.commands.ReportCommand;
import org.runnerer.reports.commands.ReportHandleCommand;
import org.runnerer.reports.common.utils.C;
import org.runnerer.reports.common.utils.F;
import org.runnerer.reports.discord.DiscordManager;
import org.runnerer.reports.discord.webhook.Webhook;

import java.util.ArrayList;

public class ReportSystem extends JavaPlugin
{
    public static ArrayList<Player> cooldownList = new ArrayList<>();
    public static ArrayList<Player> reportedPlayerList = new ArrayList<>();
    public BukkitTask task = null;

    @Override
    public void onEnable()
    {
        runCleanTask();
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("cheaterreport").setExecutor(new ReportCommand());
        getCommand("reporthandle").setExecutor(new ReportHandleCommand());
    }

    private void runCleanTask()
    {
        task = Bukkit.getScheduler().runTaskTimer(this, new Runnable()
        {
            @Override
            public void run()
            {
                if (cooldownList.size() == 0 || cooldownList == null) return;

                cooldownList.clear();
            }
        }, 20, 400);
    }

    public static void reportPlayer(Player reporter, Player hacker, String reason)
    {
        reporter.sendMessage(C.Green + "A moderator will review your report as soon as possible! Thank you for sending it!");

        for (Player pl : Bukkit.getOnlinePlayers())
        {
            if (pl.hasPermission("report.logs"))
            {
                if (reporter != pl)
                {
                    reportedPlayerList.add(hacker);
                    pl.sendMessage(C.Red + C.Bold + "REPORT RECEIVED!");
                    pl.sendMessage(C.Gray + "Reporter: " + C.Yellow + reporter
                                    .getName());
                    pl.sendMessage(C.Gray + "Rulebreaker: " + C.Yellow + hacker
                                    .getName());
                    pl.sendMessage(C.Gray + "Reason: " + C.Yellow + reason);

                    Webhook.sendMessage("Staff Report", "A report has been received on the server!" + "\n" + "Reporter: " + reporter.getName() + "\n" + "Rulebreaker: " + hacker.getName() + "\n" + "Reason: " + reason);
                }
            }
        }
    }

    public static void reportHandle(Player caller, String string, String string2, String string3)
    {
        if (ReportSystem.reportedPlayerList.contains(Bukkit.getPlayer(string3)))
        {
                Player player = Bukkit.getPlayer(string2);
                if (string.equalsIgnoreCase("accept"))
                {
                    caller.sendMessage(C.Green + "Sent a handle message for you!");
                    player.sendMessage(C.Green + "Your report was accepted! The player will be punished shortly.");
                    ReportSystem.reportedPlayerList.remove(Bukkit.getPlayer(string3));
                } else if (string.equalsIgnoreCase("reject"))
                {
                    caller.sendMessage(C.Green + "Sent a handle message for you!");
                    player.sendMessage(C.Red + "Your report was rejected due to the player not cheating.");
                    ReportSystem.reportedPlayerList.remove(Bukkit.getPlayer(string3));
                } else
                {
                    caller.sendMessage(C.Gray + "[" + C.Aqua + "Report" + C.Gray + "] Incorrect arguments! /reporthandle <accept/reject> <cheater>");
                    return;
                }
                return;
            }
        }
}
