package core;

public class Lesson {

    private String name;
    private String type;
    private String location;
    private String teacherName;
    private String num;

    public Lesson(String name, String type, String location, String teacherName, String num) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.teacherName = teacherName;
        this.num = num;
    }


    @Override
    public String toString() {
        return "Num: " + num
                + " Name: " + name
                + " Location: " + location
                + " Teacher: " + teacherName
                +"\n";

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Lesson() {

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getNum() {
        return num;
    }
}
