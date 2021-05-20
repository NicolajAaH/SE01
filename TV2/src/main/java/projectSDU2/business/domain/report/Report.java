package projectSDU2.business.domain.report;

import com.google.gson.Gson;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.initialize.CreditingSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Report {
    //Attributter
    protected CreditingSystem cs = CreditingSystem.getInstance();
    protected Gson gson = new Gson();
    protected DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    protected Date today = new Date();
    protected String todayNoTime = format.format(today);
    protected String nameOfFile = todayNoTime + " ID ";

    //Skriver til og opretter en fil
    protected void writeToFile(String fileName, ArrayList<Production> list) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(gson.toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
