package anais.springboot.sample.lunchmenu.model;

public class LunchMenu {

    private int id;
    private String name;
    private int cafeteriaId;
    private int calorie;

    public LunchMenu() {}

    public LunchMenu(int id, String name, int cafeteriaId, int calorie) {
        this.id = id;
        this.name = name;
        this.cafeteriaId = cafeteriaId;
        this.calorie = calorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCafeteriaId() {
        return cafeteriaId;
    }

    public void setCafeteriaId(int cafeteriaId) {
        this.cafeteriaId = cafeteriaId;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    @Override
    public String toString() {
        return "LunchMenu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cafeteriaId=" + cafeteriaId +
                ", calorie=" + calorie +
                '}';
    }
}
