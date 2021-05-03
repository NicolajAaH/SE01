package projectSDU2.domain.credit;

import java.util.ArrayList;

public class Production {
    private int productionID;
    private ArrayList<Credit> credits;
    private boolean status;
    private boolean sent;
    private String company;
    private String name;

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

    public Credit findCredit(int id){
        for (Credit credit : credits){
            if(credit.getCreditID() == id){
                return credit;
            }
        }
        return null;
    }

    public Production(int productionID, ArrayList<Credit> credits, String company, String name){
        this.productionID = productionID;
        this.credits = credits;
        this.company = company;
        this.name = name;
        status = false;
        sent = false;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isSent() {
        return sent;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return ""+productionID;
    }
}