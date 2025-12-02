package atlix.model.beans;

import atlix.model.beans.sub.ProductSupplier;
import atlix.model.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(length = 13, unique = true, nullable = false)
    private String barcode;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "url_img")
    private String urlImg;

    @Column(nullable = false)
    private Integer stock = 0;

    @Version
    private Long version;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSupplier> suppliers = new ArrayList<>();
}
