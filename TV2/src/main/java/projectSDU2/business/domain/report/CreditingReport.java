package projectSDU2.business.domain.report;

import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.initialize.CreditingSystem;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreditingReport extends Report {

    private CreditingSystem cs = CreditingSystem.getInstance();
    private Gson gson = new Gson();
    private ArrayList<Production> productionsToSend;
    private DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private Date today = new Date();
    private String todayNoTime = format.format(today);
    private static int index = 0;
    private String nameOfFile = "Krediteringsrapport " + todayNoTime + " ID " + index;

    public void generateCreditingReport() {
        productionsToSend = new ArrayList<>();
        for (Production production : cs.getProductions()) {
            while (production.isSent() == false && production.isStatus() == true) {
                productionsToSend.add(production);
                production.setSent(true);
                cs.setSentProduction(production);
            }
        }
        writeToFile(nameOfFile);
        index++;
        //System.out.println("All verified credits have been sent");
    }

    private void writeToFile(String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(gson.toJson(productionsToSend));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
