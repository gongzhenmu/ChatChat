package model;

import java.util.ArrayList;

public class Chatroom {

        private String category;
        private String name;
        private String description;
        private int likes;
        private String creater_name;
        private String date;
        private ArrayList<Message> messages;
        public Chatroom()
        {

        }

        public Chatroom(String name, String category, String creater_name, String date)
        {
            this.name = name;
            this.category = category;
            this.creater_name = creater_name;
            this.date = date;
            likes = 0;
            messages = new ArrayList<>();

        }

        public void addMessage(Message message)
        {
            messages.add(message);
        }

        public void setDescription(String des)
        {
            description = des;
        }

        public void like()
        {
            likes++;
        }

        public String getName(){return name;}
        public String getCategory(){return category;}
        public String getCreater_name(){return creater_name;}
        public String getDescription(){return description;}
        public ArrayList<Message> getMessages(){return messages;}
        public int getLikes(){return likes;}

}
