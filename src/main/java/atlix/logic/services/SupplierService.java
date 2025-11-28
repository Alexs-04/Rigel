package atlix.logic.services;

import atlix.model.beans.Supplier;
import atlix.model.repositories.SupplierRepository;
import atlix.model.response.SupplierDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierDTO::toDTO)
                .toList();
    }

    @Transactional
    public SupplierDTO findByName(String name) {
        return supplierRepository.findByName(name)
                .map(SupplierDTO::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with name " + name + " not found"));
    }

    @Transactional
    public void save(SupplierDTO supplierDTO) {
        var p = supplierRepository.findByName(supplierDTO.name());

        if (p.isPresent()) {
            throw new IllegalArgumentException("Supplier with name " + supplierDTO.name() + " already exists");
        }

        var  supplier = new Supplier();
        supplier.setName(supplierDTO.name());
        supplier.setEmail(supplierDTO.email());
        supplier.setAddress(supplierDTO.address());
        supplier.setPhone(supplierDTO.phone());
        supplier.setProducts(new ArrayList<>());

        supplierRepository.save(supplier);
    }

    @Transactional
    public void delete(SupplierDTO supplierDTO) {
        var p  = supplierRepository.findByName(supplierDTO.name());
        if (p.isEmpty()) {
            throw new IllegalArgumentException("Supplier with name " + supplierDTO.name() + " not found");
        }

        supplierRepository.delete(p.get());
    }
}