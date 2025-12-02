package atlix.model.request;

import atlix.model.response.ProductDTO;

import java.io.Serializable;

public record ProductRequest(
   ProductDTO productDTO,
   String supplierName,
   Double price
) implements Serializable {}
