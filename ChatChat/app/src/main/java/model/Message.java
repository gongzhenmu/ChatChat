package model;

public class Message {
    private String message;
    private String username;
    private String url;
    private String user_id;
    private String date;

    public Message()
    {
        message = "";
        username = "";
        url = "" ;
        user_id = "";

    }

    public Message(String message, String username, String url, String user_id,String date)
    {
        this.message = message;
        this.user_id = user_id;
        this.url = url;
        this.username = username;
        this.date = date;
    }

    public String getMessage()
    {
        return message;
    }

    public String getUsername()
    { return  username;}

    public String getUrl(){return url;}

    public String getUser_id(){return user_id;}

    public String getDate(){return date;}

}
