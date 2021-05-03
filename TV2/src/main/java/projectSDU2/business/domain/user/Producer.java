package projectSDU2.business.domain.user;

import projectSDU2.business.domain.credit.Production;

import java.util.ArrayList;

public class Producer extends Person{
    private String company;
    private ArrayList<Production> productions;

    public Producer(String name, int phone, int ID, String email, String company, ArrayList<Production> productions){
        this.name = name;
        this.phone = phone;
        this.id = ID;
        this.email = email;
        this.company = company;
        this.productions = productions;
    }

    public Producer(String name, int phone, int oid, String email, String company) {
        this.name = name;
        this.phone = phone;
        this.id = oid;
        this.email = email;
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public Producer(String name, int phone, String email, String company, ArrayList<Production> productions){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.productions = productions;
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    @Override
    public String toString(){
        return "ID: " + id + "\t" + "Name: " + name + "\t" + "Email: " + email + "\t" + "Phone: " + phone + "\t" + "Company: " + company;
    }
}
