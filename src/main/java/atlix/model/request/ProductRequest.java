package atlix.model.request;

import atlix.model.response.ProductDTO;

public record ProductRequest(
   ProductDTO productDTO,
   String supplierName,
   Double price
) {}
