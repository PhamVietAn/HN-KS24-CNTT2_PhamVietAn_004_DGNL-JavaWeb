package ra.edu.hnks24cntt2_phamvietan_004.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.edu.hnks24cntt2_phamvietan_004.model.Product;

import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(String keyword, Pageable pageable);

    Optional<Product> findById(Long id);

    Product save(Product product);

    void deleteById(Long id);
}
