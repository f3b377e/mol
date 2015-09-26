import java.util.Comparator;

/**
 * Class that describe a product will be transported.
 * One Good have one unique id, a volume, quantity of the object
 * and finally the description of the obj
 */
public class Good {
    private Integer id;
    private double volume;
    private Integer qnt;
    private String description;

    public Good(Integer id, double volume, Integer qnt, String description) {
        this.id = id;
        this.volume = volume;
        this.qnt = qnt;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Integer getQnt() {
        return qnt;
    }

    public void setQnt(Integer qnt) {
        this.qnt = qnt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * create and implement a new comparator for goods object.
     * @return comparator of 2 Good
     */
    public static Comparator<Good> comparator(){
        return (o1, o2) -> {
            if (o1.getVolume() < o2.getVolume())
                return -1;
            if (o1.getVolume() > o2.getVolume())
                return 1;
            return 0;
        };
    }
}
