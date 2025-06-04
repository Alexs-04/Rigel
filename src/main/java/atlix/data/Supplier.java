package atlix.data;

public record Supplier(
        int id,
        String company,
        String name,
        String nameContact,
        String email
) {
    public Supplier {
        if (id <= 0) {
            throw new IllegalArgumentException("Supplier id cannot be zero");
        }
        if (company == null) {
            throw new IllegalArgumentException("Supplier company cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Supplier name cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Supplier email cannot be null");
        }
    }
}
