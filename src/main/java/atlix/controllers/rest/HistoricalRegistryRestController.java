package atlix.controllers.rest;

import atlix.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/historical")
public class HistoricalRegistryRestController {
    private final SaleService saleService;

    public HistoricalRegistryRestController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<?> getHistoricalRegistry(@PathVariable LocalDateTime date) {
        var registry = saleService.findAll()
                .stream()
                .filter(x -> x.getDataTime().equals(date)
        ).toList();
        return ResponseEntity.ok(registry);
    }

}
