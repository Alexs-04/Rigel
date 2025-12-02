package atlix.model.request;

import java.io.Serializable;

public record SaleItemRequest(
        Long productId,
        Integer qty,
        Double unitPrice
) implements Serializable {
}

