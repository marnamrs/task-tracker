package todo.project.todotracker.models.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * AttributeValue:
 * Values of the additional fields/columns created by Users
 * */

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AdditionalAttribute attribute;

    private String string;
    private int numInt;
    private double numDouble;
    private boolean bool;

    public AttributeValue(Task task, AdditionalAttribute attribute) {
        setTask(task);
        setAttribute(attribute);
        task.setLastEdit();
    }

    public AdditionalAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(AdditionalAttribute attribute) {
        this.attribute = attribute;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getNumInt() {
        return numInt;
    }

    public void setNumInt(int numInt) {
        this.numInt = numInt;
    }

    public double getNumDouble() {
        return numDouble;
    }

    public void setNumDouble(double numDouble) {
        this.numDouble = numDouble;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
