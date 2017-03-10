package object;
import java.util.Set;
/**
 * @author Vlad Shevchenko
 * @version 1.0
 * @since 07.03.17
 */
public class Obj {

    private String value;

    private Set<String> concatWords;

    public Obj(String value, Set<String> concatWords) {
        this.value = value;
        this.concatWords = concatWords;
    }

    public Obj(String word) {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<String> getConcatWords() {
        return concatWords;
    }

    public void setConcatWords(Set<String> concatWords) {
        this.concatWords = concatWords;
    }

    @Override
    public String toString() {
        return "Obj{" +
                "value='" + value + '\'' +
                ", concatWords=" + concatWords +
                '}';
    }
}
