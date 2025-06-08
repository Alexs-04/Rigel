package atlix.concurrence;

import atlix.logic.services.ProductsService;
import atlix.ui.InventoryUI;

import java.time.Duration;

public class NotificationService implements Runnable {
    private final ProductsService productsService = new ProductsService();

    @Override
    public void run() {
        try {
            Thread.sleep(Duration.ofSeconds(5).toMillis()); // Espera 5 segundos antes de verificar
            if (!productsService.isProductLowStock()) {
                Notification notification = new Notification("Alerta de inventario bajo", "Un producto tiene inventario bajo.");
                notification.show();
                Thread.sleep(Duration.ofSeconds(10).toMillis());
                new InventoryUI().setVisible(true);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }

}
