package ra.edu.hnks24cntt2_phamvietan_004.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.edu.hnks24cntt2_phamvietan_004.model.Product;
import ra.edu.hnks24cntt2_phamvietan_004.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAll(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll(pageable);
        }
        String trimmed = keyword.trim();
        return productRepository.findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                trimmed,
                trimmed,
                pageable
        );
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }
}
