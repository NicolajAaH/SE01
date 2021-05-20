package projectSDU2.business.domain.report;

import projectSDU2.business.domain.credit.Production;

import java.util.ArrayList;

public class CreditingReport extends Report {
    //Attributter
    private ArrayList<Production> productionsToSend;
    private static int index = 1;
    private String filename = "Krediteringsrapport " + super.nameOfFile + index + ".txt";

    //Udskriver en rapport med overblik over alle validerede produktioner som ikke tidligere er indrapporteret
    public void generateCreditingReport() {
        productionsToSend = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            while (!production.isSent() && production.isStatus()) { //Hvis produktionen ikke er sendt og den er verificeret
                productionsToSend.add(production);
                production.setSent(true);
                cs.setSentProduction(production);
            }
        }
        writeToFile(filename, productionsToSend);
        index++;
    }
}
