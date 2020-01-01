package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Table {
    private HashMap<DayOfWeek, ArrayList<Lesson>> days = new HashMap<>();

    public Table() {
        days.put(DayOfWeek.MONDAY, new ArrayList<>());
        days.put(DayOfWeek.TUESDAY, new ArrayList<>());
        days.put(DayOfWeek.WEDNESDAY, new ArrayList<>());
        days.put(DayOfWeek.THURSDAY, new ArrayList<>());
        days.put(DayOfWeek.FRIDAY, new ArrayList<>());
        days.put(DayOfWeek.SATURDAY, new ArrayList<>());


    }


    public HashMap<DayOfWeek, ArrayList<Lesson>> getDays() {
        return days;
    }

    public void setDays(HashMap<DayOfWeek, ArrayList<Lesson>> days) {
        this.days = days;
    }
}
