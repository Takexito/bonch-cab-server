package core;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {

    private WebClient client;

    //private int flag = 0;
    private int week;

    public Table getTable() {
        return table;
    }

    //private ArrayList<ArrayList<Lesson>> tableList = new ArrayList<>();
    private Table table;
    private DayOfWeek currentDay;

    public Parser() {
        client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        table = new Table();
    }

    public void post(String url, String data) {
        URL urlU = null;
        try {
            urlU = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("BAD URL FOR POST");
        }
        WebRequest requestSettings = new WebRequest(urlU, HttpMethod.POST);
        requestSettings.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestSettings.setAdditionalHeader("X-Requested-With", "XMLHttpRequest");
        requestSettings.setRequestBody(data);

        try {
            Page page = client.getPage(requestSettings);
            Map<String, String> requestParameters = requestSettings.getAdditionalHeaders();
            System.out.print(requestParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String get(String url) {
        Page page;
        try {
            page = client.getPage(url);
            return page.getWebResponse().getContentAsString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean parse(String html) {
        Document doc = Jsoup.parse(html);
        Element tableE = doc.select("table.simple-little-table").first();
        if (tableE == null) return false;
        Elements tr = tableE.select("tr");
        if (tr == null) return true;
        //createTable();
        setWeek(doc);
        tr.forEach(this::createLessons);
        System.out.println("Final table for " + week);
        //flag = 0;
       // printJson();
        return true;
    }

//    private void printJson() {
//        Gson gson = new Gson();
//        String json = gson.toJson(tableList);
//        System.out.println(json);
//    }

    private void setWeek(Element element) {
        Elements select = element.select("h2");
        String br = select.text();
        int first = br.indexOf('№');
        br = br.substring(first + 1, first + 5);
        week = Integer.parseInt(br.substring(0, br.indexOf(' ')));
    }

    private void createLessons(Element element) {
        if (check(element.text()) != -1) return;

        Lesson lesson = new Lesson();
        Elements el = element.select("td");
        List<String> str = el.eachText();

        if (str.size() == 4) {
            lesson.setNum(str.get(0));
            lesson.setName(str.get(1));
            lesson.setLocation(str.get(2));
            lesson.setTeacherName(str.get(3));
        }

        if (str.size() == 3) {
            lesson.setNum(str.get(0));
            lesson.setName(str.get(1));
            lesson.setLocation(str.get(2));
        }

        if (str.size() == 2) {
            lesson.setNum(str.get(0));
            lesson.setName(str.get(1));
        }

        if (str.size() == 1) {
            lesson.setNum(str.get(0));
        }

        table.getDays().get(currentDay).add(lesson);
        //tableList.get(flag).add(lesson);
    }

//    private void createTable() {
//        tableList.add(new ArrayList<>());
//        tableList.add(new ArrayList<>());
//        tableList.add(new ArrayList<>());
//        tableList.add(new ArrayList<>());
//        tableList.add(new ArrayList<>());
//        tableList.add(new ArrayList<>());
//    }

    private int check(String string) {
        String str = string.toLowerCase();
        if (str.startsWith("по")) {
            currentDay = DayOfWeek.MONDAY;
            return 0;
        }

        if (str.startsWith("вт")) {
            currentDay = DayOfWeek.TUESDAY;
            return 1;
        }
        if (str.startsWith("ср")) {
            currentDay = DayOfWeek.WEDNESDAY;
            return 2;
        }
        if (str.startsWith("че")) {
            currentDay = DayOfWeek.THURSDAY;
            return 3;
        }
        if (str.startsWith("пя")) {
            currentDay = DayOfWeek.FRIDAY;
            return 4;
        }
        if (str.startsWith("су")) {
            currentDay = DayOfWeek.SATURDAY;
            return 5;
        }
        return -1;
    }
}