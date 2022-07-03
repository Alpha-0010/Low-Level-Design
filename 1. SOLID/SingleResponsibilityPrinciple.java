// Here all the funcnatilities are related to jounals so the jounrnal class has a single responsility.
import java.util.ArrayList;
import java.util.List;

class Journal {
    private final List<String> entries = new ArrayList<>();
    private static int cnt = 0;

    public void addEntry(String entry) {
        entries.add(""+(++cnt)+ " : " + entry);
    }

    public void removeEntry(int index) {
        entries.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }

}

public class  SingleResponsibilityPrinciple{
    public static void main(String[] args) {
        Journal J = new Journal();
        J.addEntry("I had lunch today");
        J.addEntry("The lunch was amazing!");
        System.out.println(J);
    }
}