package atlix.data;

public record Product(
        long barCode,
        String name,
        String description,
        double price,
        int stock,
        long id
) {
    public Product {
        if (barCode <= 0) {
            throw new IllegalArgumentException("Bar code must be a positive number.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative.");
        }
    }
}
