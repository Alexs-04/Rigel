package atlix.model.response;

import atlix.model.beans.Supplier;

import java.io.Serializable;
import java.util.ArrayList;

public record SupplierDTO(

        String name,
        String address,
        String phone,
        String email

) implements Serializable {
    public static SupplierDTO toDTO(Supplier supplier) {
        return new SupplierDTO(
                supplier.getName(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getEmail()
        );
    }

    @Deprecated
    public static Supplier toEntity(SupplierDTO dto) {
        return new Supplier(
                null,
                dto.name,
                dto.address,
                dto.phone,
                dto.email,
                new ArrayList<>()
        );
    }
}
