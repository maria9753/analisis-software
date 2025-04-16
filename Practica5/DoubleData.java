public class DoubleData {
    private double value;
    private double average;

    public DoubleData(double value, double average) {
        this.value = value;
        this.average = average;
    }

    public double getValue() {
        return value;
    }

    public double getAverage() {
        return average;
    }

    @Override
    public String toString() {
        return String.format("%.1f (avg.= %.3f)", value, average);
    }
}