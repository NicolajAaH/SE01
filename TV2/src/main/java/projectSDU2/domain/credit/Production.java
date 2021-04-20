package projectSDU2.domain.credit;

import java.util.ArrayList;

public class Production {
    private int productionID;
    private ArrayList<Credit> credits;
    private boolean status;
    private boolean sent;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getProductionID() {
        return productionID;
    }

    public void setProductionID(int productionID) {
        this.productionID = productionID;
    }

    public ArrayList<Credit> getCredits() {
        return credits;
    }

    public void addCredit(Credit credit) {
        credits.add(credit);
    }

    public Production(int productionID, ArrayList<Credit> credits){
        this.productionID = productionID;
        this.credits = credits;
    }
}
