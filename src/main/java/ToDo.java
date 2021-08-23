import java.util.Objects;

public class ToDo extends Task{

    public ToDo(String description) {
        super(description.trim());
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
