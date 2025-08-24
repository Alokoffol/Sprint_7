package org.example;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("firstName")
    private final String firstName;
    @SerializedName("lastName")
    private final String lastName;
    @SerializedName("address")
    private final String address;
    @SerializedName("metroStation")
    private final String metroStation;
    @SerializedName("phone")
    private final String phone;
    @SerializedName("rentTime")
    private final Integer rentTime;
    @SerializedName("deliveryDate")
    private final String deliveryDate;
    @SerializedName("comment")
    private final String comment;
    @SerializedName("color")
    private final String[] color;

    public Order(String firstName, String lastName, String address, String metroStation,
                 String phone, Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order withColor(String[] color) {
        return new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back", color);
    }

    // Геттеры (опционально, если нужны для тестов)
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public String[] getColor() {
        return color;
    }
}