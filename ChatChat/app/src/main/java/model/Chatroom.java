package model;

import java.util.ArrayList;
import java.util.Map;

public class Chatroom {
        private Map<String, String> chatroom;
    public static final String CHAT_NAME = "chatName";
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String LIKES = "likes";
    public static final String CREATER = "creater_name";
    public static final String DATE = "date";
    public static final String CHAT_ID = "chatid";

        private String category;
        private String name;
        private String description;
        private int likes;
        private String creater_name;
        private String date;
        public Chatroom()
        {

        }

        public Chatroom(String name, String category, String creater_name, String date, String chatid)
        {
            chatroom.put(CHAT_NAME, name);
            chatroom.put(CATEGORY, category);
            chatroom.put(DESCRIPTION, description);
            chatroom.put(LIKES, "0");
            chatroom.put(CREATER, creater_name);
            chatroom.put(DATE, date);
            chatroom.put(CHAT_ID, chatid);
        }

        public void setDescription(String des)
        {
            chatroom.put(DESCRIPTION, des);
        }

        public void like()
        {
            int like_num = Integer.parseInt(chatroom.get(LIKES)) + 1;
            chatroom.put(LIKES, like_num+"");

        }

        public String getName(){return chatroom.get(CHAT_NAME);}
        public String getCategory(){return chatroom.get(CATEGORY);}
        public String getCreater_name(){return chatroom.get(CREATER);}
        public String getDescription(){return chatroom.get(DESCRIPTION);}
        public String getLikes(){return chatroom.get(LIKES);}
        public String getChatId(){return chatroom.get(CHAT_ID);}

}
