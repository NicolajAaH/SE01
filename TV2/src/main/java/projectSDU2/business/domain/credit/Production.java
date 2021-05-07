package projectSDU2.business.domain.credit;

import java.util.ArrayList;

public class Production {
    private int productionID;
    private String name;
    private String company;
    private ArrayList<Credit> credits;
    private transient boolean status;
    private transient boolean sent;



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

    public Production(ArrayList<Credit> credits, String company, String name){
        this.credits = credits;
        this.company = company;
        this.name = name;
        status = false;
        sent = false;
    }

    public Production(int productionID, String company, String name, boolean status, boolean sent){
        this.productionID = productionID;
        this.credits = new ArrayList<>();
        this.company = company;
        this.name = name;
        this.status = status;
        this.sent = sent;
    }

    public Production(int productionID, String company, String name, boolean status, boolean sent, ArrayList<Credit> credits){
        this.productionID = productionID;
        this.credits = new ArrayList<>();
        this.company = company;
        this.name = name;
        this.status = status;
        this.sent = sent;
        this.credits = credits;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: "+productionID + " NAME: " + name + " Credits: \n");
        stringBuilder.append("[");
        for (Credit credit : credits){
            stringBuilder.append(credit.toString() + "\n");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
