package main.sahib.java.model;
public class Parcel {
    private String parcelId;
    private double weight;
    private String dimensions;
    private boolean isProcessed;

    public Parcel(String parcelId, double weight, String dimensions) {
        this.parcelId = parcelId;
        this.weight = weight;
        this.dimensions = dimensions;
        this.isProcessed = false;
    }

    public String getParcelId() { return parcelId; }
    public double getWeight() { return weight; }
    public String getDimensions() { return dimensions; }
    public boolean isProcessed() { return isProcessed; }

    public void markProcessed() { this.isProcessed = true; }
}