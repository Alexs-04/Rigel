package atlix.model.request;

import java.io.Serializable;
import java.util.List;

public record SalePayloadRequest(
        List<SaleItemRequest> items,
        Double subtotal,
        Double taxes,
        Double total
) implements Serializable {
}

