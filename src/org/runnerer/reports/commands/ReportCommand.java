package org.runnerer.reports.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.runnerer.reports.ReportSystem;
import org.runnerer.reports.common.utils.C;
import org.runnerer.reports.common.utils.F;

public class ReportCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player)) return false;

        if (cmd.getName().equalsIgnoreCase("report") || cmd.getName().equalsIgnoreCase("cheaterreport"))
        {

            if (args == null || args.length == 0 || args.length > 2)
            {
                sender.sendMessage(C.Gray + "[" + C.Aqua + "Report" + C.Gray + "] Incorrect arguments! /report <player> <reason>");
                return true;
            }

            if (ReportSystem.cooldownList.contains(sender))
            {
                sender.sendMessage(C.Gray + "[" + C.Aqua + "Report" + C.Gray + "] Please wait before doing your next report!");
                return true;
            }

            Player rulebreaker = Bukkit.getPlayer(args[0]);

            if (rulebreaker == null)
            {
                sender.sendMessage(C.Red + "That player doesn't seem to be online.");
                return true;
            }

            if (!ReportSystem.reportedPlayerList.contains(rulebreaker))
            {
                ReportSystem.reportedPlayerList.add(rulebreaker);
            }

            String reason = F.combine(args, 1);

            ReportSystem.reportPlayer((Player) sender, rulebreaker, reason);
            return true;
        }
        return false;
    }
}
