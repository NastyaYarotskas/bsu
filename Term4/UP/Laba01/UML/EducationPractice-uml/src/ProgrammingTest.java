import java.util.HashSet;

public class ProgrammingTest {

    public static void main(String[] args) {

        Academic ac1 = new Academic("ac1");
        Academic ac2 = new Academic("ac2");
        Academic ac3 = new Academic("ac3");

        Undergraduate st1 = new Undergraduate("name1", "login1", "email1", ac3);
        Undergraduate st2 = new Undergraduate("name2", "login2", "email2", ac3);
        Postgraduate st3 = new Postgraduate("name3", "login3", "email3", ac1);
        Postgraduate st4 = new Postgraduate("name4", "login4", "email4", ac1);
        Postgraduate st5 = new Postgraduate("name5", "login5", "email5", ac2);

        HashSet<Student> students = new HashSet<>();
        students.add(st1);
        students.add(st2);
        students.add(st3);
        students.add(st4);
        students.add(st5);

        Course course = new Course("course1", students);

        course.getPostgraduates("ac1");

        System.out.println(course.getPostgraduates("ac1") + "");

        Notifier noty = new Notifier(course.getPostgraduates("ac1"));
        noty.doNotifyAll("Hello!");

    }
}
