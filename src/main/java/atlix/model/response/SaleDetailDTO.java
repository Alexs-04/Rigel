package atlix.model.response;

import java.io.Serializable;

public record SaleDetailDTO(
        Long productId,
        Integer quantity,
        Double unitPrice
) implements Serializable {
}
