package nilH.easyTrackDiet.model;

import java.io.Serializable;
import java.sql.Time;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table
public class Record implements Serializable{
    @Id
    private int record_id;
    private int food_id;
    private int amount;
    private Time timestamp;
    public int getRecord_id() {
        return record_id;
    }
    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }
    public int getFood_id() {
        return food_id;
    }
    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Time getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Time timestamp) {
        this.timestamp = timestamp;
    }
}
