package org.runnerer.reports.common.utils;

import org.bukkit.ChatColor;

public class F
{
    public static String combine(String[] args, int start)
    {
        String out = "";

        for (int i = start; i < args.length; ++i)
        {

            out = out + args[i];

            out = out + " ";
        }
        if (out.length() > 0)
        {
            out = out.substring(0, out.length() - 1);
        }
        return out;
    }
}
