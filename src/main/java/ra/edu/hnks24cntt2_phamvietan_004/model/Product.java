package ra.edu.hnks24cntt2_phamvietan_004.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm bắt buộc.")
    @Size(min = 5, max = 150, message = "Tên sản phẩm phải từ 5 đến 150 ký tự.")
    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @NotBlank(message = "Danh mục bắt buộc.")
    @Pattern(
            regexp = "^(Điện tử|Thời trang|Gia dụng)$",
            message = "Danh mục chỉ nhận: Điện tử, Thời trang, Gia dụng."
    )
    @Column(nullable = false, length = 50)
    private String category;

    @NotNull(message = "Giá bắt buộc.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0.")
    @DecimalMax(value = "100000000", message = "Giá không quá 100000000 VNĐ.")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Tồn kho bắt buộc.")
    @Min(value = 0, message = "Tồn kho phải là số nguyên không âm.")
    @Column(nullable = false)
    private Integer stock;

    @Size(max = 500, message = "Mô tả tối đa 500 ký tự.")
    @Column(length = 500)
    private String description;
}
