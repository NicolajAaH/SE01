package projectSDU2.business.domain.email;

public class Notification {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification(String message) {
        this.message = message;
    }

    public void sendNotification(){
        //TODO
    }
}
