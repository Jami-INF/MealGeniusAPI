package entity;

public class StepEntity {
    private String id;
    private String description;
    private int number;
    private int duration;

    public StepEntity(String id, String description, int number, int duration) {
        this.id = id;
        this.description = description;
        this.number = number;
        this.duration = duration;
    }

    public StepEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
