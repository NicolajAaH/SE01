package projectSDU2.business.domain.report;

import com.google.gson.Gson;
import projectSDU2.business.domain.credit.Credit;
import projectSDU2.business.domain.initialize.CreditingSystem;
import projectSDU2.business.domain.user.Person;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FinanceReport extends Report{
    private ArrayList<Person> top10credits;
    private HashMap<Types, Integer> creditTypes;
    private HashMap<String, ArrayList<Credit>> producers;
    private CreditingSystem cs = CreditingSystem.getInstance();
    private Gson gson = new Gson();
    private Date today = new Date();
    private DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private String todayNoTime = format.format(today);
    private static int index = 0;
    private String nameOfFile = "Krediteringsrapport " + todayNoTime + " ID " + index;

    public void generateFinanceReport(){
        top10credits = new ArrayList<>();
        creditTypes = new HashMap<>();
        producers = new HashMap<>();


    }


}
