package projectSDU2.domain.user;

import projectSDU2.domain.credit.Production;

import java.util.ArrayList;

public class Producer {
    private String company;
    private ProducerLogin producerLogin;
    private ArrayList<Production> productions;

    public ArrayList<Production> getProductions() {
        return productions;
    }
}
