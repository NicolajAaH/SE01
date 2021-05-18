package projectSDU2.business.domain.report;

import com.google.gson.Gson;
import projectSDU2.business.domain.credit.Production;
import projectSDU2.business.domain.credit.Roles;
import projectSDU2.business.domain.initialize.CreditingSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Report {
    private int reportID;

    protected static CreditingSystem cs = CreditingSystem.getInstance();
    protected static Gson gson = new Gson();
    protected static DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    protected static Date today = new Date();
    protected static String todayNoTime = format.format(today);
    protected static String nameOfFile = "Krediteringsrapport " + todayNoTime + " ID ";


    protected void writeToFile(String fileName, ArrayList<Production> list) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(gson.toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected static void writeToFile(String fileName, HashMap<String, HashMap<Roles, Integer>> map) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(gson.toJson(map));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
