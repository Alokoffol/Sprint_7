package org.example.models;

import com.google.gson.annotations.SerializedName;

public class CourierCredentials {
    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    @SerializedName("firstName")
    private String firstName;

    public CourierCredentials(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // Геттеры
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    // Сеттеры (если нужны)
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Фабричный метод для логина (без firstName)
    public static CourierCredentials fromLogin(String login, String password) {
        return new CourierCredentials(login, password, null);
    }
}