package org.example.model;

public class CourierCreds {
    private String login;
    private String password;

    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFrom(Courier courier){
        return new CourierCreds(courier.getLogin(), courier.getPassword());

    }
    public static CourierCreds specificLogin(Courier courier, String login){
        return new CourierCreds(login, courier.getPassword());

    }
    public static CourierCreds specificPassword(Courier courier, String password){
        return new CourierCreds(courier.getLogin(), password);

    }
}
