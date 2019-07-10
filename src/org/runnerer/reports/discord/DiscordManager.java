package org.runnerer.reports.discord;

import org.runnerer.reports.discord.helper.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscordManager {

    private enum EHttpMethod {
        GET,
        POST
    };

    private Logger logger = Logger.getLogger(DiscordManager.class.getName());
    private String userAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0";

    private URL _url;

    public DiscordManager(String url) throws MalformedURLException {
        this._url = new URL(url + "/slack");
    }

    private HttpURLConnection beginConnection(EHttpMethod method) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) this._url.openConnection();

            connection.setRequestMethod(method.toString());
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setRequestProperty("Content-type", "application/json; charset=windows-1252");
            connection.setDoOutput(true);
            connection.setDoInput(true);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            connection = null;
        }
        return (connection);
    }

    private boolean doPost(String payload) {
        HttpURLConnection connection = beginConnection(EHttpMethod.POST);
        if (connection == null) {
            return (false);
        }
        connection.setRequestProperty("Content-length", String.valueOf(payload.length()));
        DataOutputStream outputStream;
        try {
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(payload);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return (false);
        }
        try {
            if (connection.getResponseCode() != 200) {
                return (false);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return (false);
        }
        return (true);
    }

    public void sendMessage(Message msg) throws IOException {
        if (!this.doPost(msg.toJson().toString())) {
            throw new IOException("Oops, we've hit a snag.");
        }
    }

}