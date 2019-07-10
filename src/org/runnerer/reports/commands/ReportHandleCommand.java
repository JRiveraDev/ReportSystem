package org.runnerer.reports.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.runnerer.reports.ReportSystem;
import org.runnerer.reports.common.utils.C;
import org.runnerer.reports.common.utils.F;

import java.util.Map;

public class ReportHandleCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player)) return false;

        if (cmd.getName().equalsIgnoreCase("reporthandle"))
        {

            if (!sender.hasPermission("report.handle"))
            {
                sender.sendMessage(C.Red + "No permission!");
                return true;
            }

            if (args == null || args.length == 0 || args.length > 2)
            {
                sender.sendMessage(C.Gray + "[" + C.Aqua + "Report" + C.Gray + "] Incorrect arguments! /reporthandle <accept/reject> <cheater>");
                return true;
            }

            if (args.length < 2)
            {
                sender.sendMessage(C.Gray + "[" + C.Aqua + "Report" + C.Gray + "] Incorrect arguments! /reporthandle <accept/reject> <cheater>");
                return true;
            }

            if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("reject"))
            {
                Player rulebreaker = Bukkit.getPlayer(args[1]);

                if (rulebreaker == null)
                {
                    sender.sendMessage(C.Red + "That player doesn't seem to be online.");
                    return true;
                }

                Player cheater = null;
                for (Player entry : ReportSystem.reportedPlayerList)
                {
                    if (entry.getName() == args[1])
                    {
                        cheater = entry;
                    }
                }

                try
                {
                    ReportSystem.reportHandle((Player) sender, args[0], cheater.getName(), args[1]);
                }
                catch (Exception e)
                {
                    sender.sendMessage(C.Red + "That player was never reported.");
                }
                return true;
            } else
            {
                sender.sendMessage(C.Gray + "[" + C.Aqua + "Report" + C.Gray + "] Incorrect arguments! /reporthandle <accept/reject> <cheater>");
                return true;
            }
        }
        return false;
    }
}
