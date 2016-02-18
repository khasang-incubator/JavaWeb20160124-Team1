package io.khasang.wlogs.model;

/**
 * Created by Андрей on 18.02.2016.
 */
public class User {
    private int id;
    private String login;
    private String password;
    private String description;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, String description) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.description = description;
    }
}
