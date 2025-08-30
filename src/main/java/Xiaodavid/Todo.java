package Xiaodavid;

public class Todo extends Task {
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toSaveFormat() {
        return String.join(" | ",
                type.getSymbol(),              // "T"
                (isDone ? "1" : "0"),          // done flag
                description
        );
    }
}
