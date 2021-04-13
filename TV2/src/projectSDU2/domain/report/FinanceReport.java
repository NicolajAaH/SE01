package projectSDU2.domain.report;

import projectSDU2.domain.credit.Credit;
import projectSDU2.domain.user.Person;

import java.util.ArrayList;
import java.util.HashMap;

public class FinanceReport extends Report{
    private ArrayList<Person> top10credits;
    private HashMap<Types, Integer> creditTypes;
    private HashMap<String, ArrayList<Credit>> producers;

}
