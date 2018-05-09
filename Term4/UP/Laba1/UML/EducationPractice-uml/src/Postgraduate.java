import java.util.Objects;

public class Postgraduate extends Student{

    private Academic supervisor;

    public Postgraduate(String name, String login, String email, Academic supervisor) {
        super(name, login, email);
        this.supervisor = supervisor;
    }

    public Academic getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Academic supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postgraduate that = (Postgraduate) o;
        return supervisor.getName().equals(((Postgraduate) o).supervisor.getName());
    }

    @Override
    public String toString() {
        return "Postgraduate{" +
                "supervisor=" + supervisor +
                ", name='" + super.getName() + '\'' +
                '}';
    }
}
