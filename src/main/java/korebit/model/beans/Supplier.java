package korebit.model.beans;

import korebit.model.beans.sub.ProductSupplier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "suppliers")
public class Supplier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String phone;

    @Column(length = 100, unique = true)
    private String email;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSupplier> products = new ArrayList<>();
}
