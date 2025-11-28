package atlix.model.response;

import atlix.model.beans.Product;
import atlix.model.enums.Category;

import java.io.Serializable;
import java.util.ArrayList;

public record ProductDTO(
        String name,
        String description,
        Double price,
        String barcode,
        Category category
) implements Serializable {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getBarcode(),
                product.getCategory()
        );
    }

    @Deprecated
    public static Product toEntity(ProductDTO dto) {
        return new Product(
                null,
                dto.name,
                dto.description,
                dto.price,
                dto.barcode,
                dto.category,
                new ArrayList<>()
        );
    }
}

