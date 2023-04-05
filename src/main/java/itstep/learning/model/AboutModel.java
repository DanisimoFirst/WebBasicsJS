package itstep.learning.model;

import java.util.Date;

/**
 * View Model for about.jsp
 */
public class AboutModel {
    private String message ;
    private Date moment ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }
}
