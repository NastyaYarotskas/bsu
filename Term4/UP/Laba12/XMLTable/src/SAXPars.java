import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SAXPars extends DefaultHandler {

    private boolean startedGrade;
    private double sumGrades = 0;
    private int countStudents = 0;

    public void startElement (String uri, String localName, String qName, Attributes attributes ) {
        if (qName.equals("student")) {
            ++countStudents;
        } else if (qName.equals("grade")) {
            startedGrade = true;
        }
    }

    public void characters(char ch[], int start, int length)  {
        if (startedGrade) {
            String string = new String (ch,start,length);
            double grade = Double.parseDouble(string);
            sumGrades += grade;
            startedGrade = false;
        }
    }

    public String getResult() {
        String result = "Total number of students: " + countStudents + "\n";
        if (countStudents > 0) {
            result += "Average grade: " + Double.toString((getAverageGrade())) + "\n";
        }
        return result;
    }

    private double getAverageGrade() {
        return sumGrades / countStudents;
    }
}