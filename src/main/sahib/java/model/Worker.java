package main.sahib.java.model;

public class Worker {
    // Method to calculate the processing fee for a parcel
    public double calculateFee(Parcel parcel) {
        double baseRate = 2.5; // Example: $2.5 per kg
        return parcel.getWeight() * baseRate;
    }

    // Method to process a parcel
    public void processParcel(Parcel parcel) {
        // Mark the parcel as processed
        parcel.markProcessed();

        // Calculate processing fee
        double fee = calculateFee(parcel);

        // Log the processing event
        Log.getInstance().addEntry(
            "Processed Parcel ID: " + parcel.getParcelId() +
            ", Fee: $" + fee
        );
    }
}
