import java.io.Serializable;

public class Student implements Serializable {
	
	private int id;
    private String name;
    private String faculty;
    private int year;
    private double meanGrade;
    
    
    public Student(int id, String name, String faculty, int year, double meanGrade) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.year = year;
        this.meanGrade = meanGrade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getYear() {
        return year;
    }

    public double getMeanGrade() {
        return meanGrade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id + "" +
                ", name='" + name + "'" +
                ", faculty='" + faculty + "'" +
                ", year=" + year +
                ", meanGrade=" + meanGrade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        return id == student.id;
    }

}
