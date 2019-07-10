package org.runnerer.reports.discord.webhook;

import org.runnerer.reports.discord.DiscordManager;
import org.runnerer.reports.discord.helper.Message;

import java.io.IOException;
import java.net.MalformedURLException;

public class Webhook
{
    public static void sendMessage(String name, String message){
        DiscordManager hk = null;
        try
        {
            hk = new DiscordManager("YOUR HOOK");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        //end of it
        Message msg = new Message(name);
        msg.setText(message);
        try
        {
            hk.sendMessage(msg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
