package projectSDU2.business.domain.report;

import projectSDU2.business.domain.credit.Production;
import java.util.ArrayList;

public class CreditingReport extends Report {
    private ArrayList<Production> productionsToSend;
    private static int index = 1;
    private String filename = "Krediteringsrapport " + super.nameOfFile + index;


    public void generateCreditingReport() {
        productionsToSend = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            while (production.isSent() == false && production.isStatus() == true) {
                productionsToSend.add(production);
                production.setSent(true);
                cs.setSentProduction(production);
            }
        }
        writeToFile(filename, productionsToSend);
        index++;
    }


}
