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
public class AdditionalValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AdditionalField field;

    private String valueString;
    private int valueInt;
    private double valueDouble;
    private boolean valueBool;

    public AdditionalValue(Task task, AdditionalField field) {
        setTask(task);
        setField(field);
        task.setLastEdit();
    }

    public AdditionalField getField() {
        return field;
    }

    public void setField(AdditionalField field) {
        this.field = field;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public boolean isValueBool() {
        return valueBool;
    }

    public void setValueBool(boolean valueBool) {
        this.valueBool = valueBool;
    }
}
