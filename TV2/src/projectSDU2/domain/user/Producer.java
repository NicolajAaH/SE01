package projectSDU2.domain.user;

import projectSDU2.domain.credit.Production;

import java.util.ArrayList;

public class Producer extends Person{
    private String company;
    private ProducerLogin producerLogin = new ProducerLogin(this);
    private ArrayList<Production> productions;

    Producer(String name, int phone, int ID, String email, String company, ArrayList<Production> productions){
        this.name = name;
        this.phone = phone;
        this.id = ID;
        this.email = email;
        this.company = company;
        this.productions = productions;
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }
}
