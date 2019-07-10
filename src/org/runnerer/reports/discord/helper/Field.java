package org.runnerer.reports.discord.helper;

import org.json.simple.JSONObject;

public class Field {

    private String _title;
    private String _value;

    public Field() {
        this(null, null);
    }

    public Field(String title) {
        this(title, null);
    }

    public Field(String title, String value) {
        this._title = title;
        this._value = value;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public void setValue(String value) {
        this._value = value;
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("title", this._title);
        result.put("value", this._value);
        return (result);
    }

}