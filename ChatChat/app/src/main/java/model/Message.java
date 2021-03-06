package model;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private Map<String, String> message = new HashMap<>();
    public static final String CONTENT = "content";
    public static final String USERNAME = "username";
    public static final String URL = "url";
    public static final String USER_ID = "user_id";
    public static final String DATE = "date";
    public static final String TIMESTAMP = "timestamp";


    public Message()
    {

    }
    public Message(String mes, String username, String url, String user_id,String date)
    {
        message.put(CONTENT, mes);
        message.put(USERNAME, username);
        message.put(URL, url);
        message.put(USER_ID, user_id);
        message.put(DATE, date);

    }

    public Map<String, String> getMessage(){return message;}

    public String getContent()
    {
        return message.get(CONTENT);
    }

    public String getUsername()
    {
        return message.get(USERNAME);
    }

    public String getUserId()
    {
        return message.get(USER_ID);
    }

    public String getUrl()
    {
        return message.get(URL);
    }

    public String getDate()
    {
        return message.get(DATE);
    }

    public void setTimestamp(String timestamp){message.put(TIMESTAMP, timestamp);}

    public String getTimestamp(){return message.get(TIMESTAMP);}
}
