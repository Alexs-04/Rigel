package atlix.concurrence;

import atlix.logic.services.ProductsService;

public class NotificationService implements Runnable {
    private final ProductsService productsService = new ProductsService();

    @Override
    public void run() {
        try {
            Thread.sleep(5000); // Espera 5 segundos antes de verificar
            if (!productsService.isProductLowStock()) {
                Notification notification = new Notification("Alerta de inventario bajo", "Un producto tiene inventario bajo.");
                notification.show();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }
}
