import java.util.HashSet;
import java.util.Set;

public class Course {

    private Set<Student> students;
    private String name;

    public Course(String name, Set<Student> students) {
        this.students = students;
        this.name = name;
    }

    public Set<Postgraduate> getPostgraduates(String nameOfSupervisor){
        Set<Postgraduate> set = new HashSet();
        for(Student student : students){
            if(student instanceof Postgraduate) {
                Postgraduate temp = (Postgraduate) student;
                if(temp.getSupervisor().getName().equals(nameOfSupervisor)){
                    set.add(temp);
                }
            }
        }

        return set;
    }
}
