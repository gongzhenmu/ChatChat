package model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private Map<String, String> user = new HashMap<>();
    public static final String CHAT_NAME = "chatName";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_ID = "userId";

    private String chatName;
    private String userEmail;
    private String userId;

    public User(String chatName, String userEmail, String userId) {
        user.put(CHAT_NAME, chatName);
        user.put(USER_EMAIL, userEmail);
        user.put(USER_ID, userId);
    }

    public Map<String, String> getUser() {
        return user;
    }

    public String getChatName() {
        return user.get(CHAT_NAME);
    }

    public String getUserId() {
        return user.get(USER_ID);
    }

    public String getUserEmail() {
        return user.get(USER_EMAIL);
    }

    public void setChatName(String chatName) {
        user.put(CHAT_NAME, chatName);
    }

    public void setUserId(String userId) {
        user.put(USER_EMAIL, userEmail);
    }

    public void setUserEmail(String userEmail) {
        user.put(USER_ID, userId);
    }
}
