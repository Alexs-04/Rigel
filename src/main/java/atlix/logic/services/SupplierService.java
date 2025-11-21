package atlix.logic.services;

import atlix.model.beans.Supplier;
import atlix.model.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveSupplier(Supplier supplier) {
        repository.save(supplier);
    }

    public List<Supplier> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Supplier findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Supplier findByName(String name) {
        return repository.findByName(name).orElse(null);
    }
}
