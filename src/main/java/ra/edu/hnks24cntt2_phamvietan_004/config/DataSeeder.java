package ra.edu.hnks24cntt2_phamvietan_004.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ra.edu.hnks24cntt2_phamvietan_004.model.Product;
import ra.edu.hnks24cntt2_phamvietan_004.repository.ProductRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return;
        }

        List<Product> products = List.of(
                Product.builder()
                        .productName("Laptop Gaming Nitro 5")
                        .category("Điện tử")
                        .price(22500000.0)
                        .stock(12)
                        .description("Laptop hiệu năng cao cho học tập và giải trí.")
                        .build(),
                Product.builder()
                        .productName("Tai nghe AirPods Pro 3")
                        .category("Điện tử")
                        .price(3950000.0)
                        .stock(40)
                        .description("Tai nghe không dây, pin bền, âm bass mạnh.")
                        .build(),
                Product.builder()
                        .productName("Áo khoác gió Uniqilo")
                        .category("Thời trang")
                        .price(620000.0)
                        .stock(25)
                        .description("Áo khoác chống gió, nhẹ, dễ phối đồ.")
                        .build(),
                Product.builder()
                        .productName("Giày bóng chuyền Asic")
                        .category("Thời trang")
                        .price(1350000.0)
                        .stock(30)
                        .description("Đế êm, phù hợp vận động mạnh.")
                        .build(),
                Product.builder()
                        .productName("Nồi chiên không dầu 5L")
                        .category("Gia dụng")
                        .price(1850000.0)
                        .stock(18)
                        .description("Dung tích lớn, nhiều chế độ nấu.")
                        .build(),
                Product.builder()
                        .productName("Máy lọc không khí Mini")
                        .category("Gia dụng")
                        .price(2150000.0)
                        .stock(10)
                        .description("Lọc bụi mịn, khử mùi, phù hợp phòng nhỏ.")
                        .build(),
                Product.builder()
                        .productName("Bếp từ đơn SmartCook")
                        .category("Gia dụng")
                        .price(1980000.0)
                        .stock(22)
                        .description("Bếp từ nhỏ gọn, tiết kiệm điện.")
                        .build(),
                Product.builder()
                        .productName("Sạc nhanh 65W PD")
                        .category("Điện tử")
                        .price(650000.0)
                        .stock(55)
                        .description("Sạc nhanh chuẩn PD, an toàn cho thiết bị.")
                        .build(),
                Product.builder()
                        .productName("Áo thun basic cotton")
                        .category("Thời trang")
                        .price(180000.0)
                        .stock(80)
                        .description("Chất cotton mềm, thấm hút tốt.")
                        .build(),
                Product.builder()
                        .productName("Bàn là hơi nước 2200W")
                        .category("Gia dụng")
                        .price(890000.0)
                        .stock(15)
                        .description("Làm phẳng nhanh, an toàn cho nhiều loại vải.")
                        .build()
        );

        productRepository.saveAll(products);
    }
}
