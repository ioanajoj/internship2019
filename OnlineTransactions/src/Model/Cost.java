package Model;

/**
 * @author joj on 4/19/2019
 **/
public class Cost {
    private double fee;
    private double tva;

    public Cost(double fee, double tva) {
        this.fee = fee;
        this.tva = tva;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "fee=" + fee +
                ", tva=" + tva +
                '}';
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }
}
