package model;

import java.util.Map;

public class Message {
    private Map<String, String> message;
    public static final String MESSAGE = "message";
    public static final String USERNAME = "username";
    public static final String URL = "url";
    public static final String USER_ID = "user_id";
    public static final String DATE = "date";
    private String content;
    private String username;
    private String url;
    private String user_id;
    private String date;


    public Message(String mes, String username, String url, String user_id,String date)
    {
        message.put(MESSAGE, mes);
        message.put(USERNAME, user_id);
        message.put(URL, url);
        message.put(USER_ID, user_id);
        message.put(DATE, date);

    }

    public String getMessage()
    {
        return message.get(MESSAGE);
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
}
