package korebit.model.request;

import korebit.model.response.ProductDTO;

public record ProductRequest(
   ProductDTO productDTO,
   String supplierName,
   Double price
) {}
