package itstep.learning.model;

import java.util.Date;

public class FormsModel {
    private String text;
    private double number;
    private Date date;
    private String color;
    private String method;
    public double getNumber() {
        return number;
    }
    public String getText() {
        return text;
    }
    public void setText(String message) {
        this.text = message;
    }
    public void setNumber(double number) {
        this.number = number;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
