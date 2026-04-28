package ra.edu.hnks24cntt2_phamvietan_004.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.hnks24cntt2_phamvietan_004.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String productName,
            String category,
            Pageable pageable
    );
}
