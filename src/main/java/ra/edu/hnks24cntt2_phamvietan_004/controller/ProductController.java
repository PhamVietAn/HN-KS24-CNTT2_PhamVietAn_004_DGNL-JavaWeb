package ra.edu.hnks24cntt2_phamvietan_004.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.edu.hnks24cntt2_phamvietan_004.model.Product;
import ra.edu.hnks24cntt2_phamvietan_004.service.ProductService;

import jakarta.annotation.PostConstruct;
import ra.edu.hnks24cntt2_phamvietan_004.model.Product;
import java.util.List;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/products","/"})
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostConstruct
    public void seedSampleProducts() {
        if (productService.findAll(null, PageRequest.of(0, 1)).getTotalElements() > 0) {
            return;
        }
        List<Product> products = List.of(
                Product.builder().productName("Laptop Gaming Nitro 5").category("Điện tử").price(22500000.0).stock(12).description("Laptop hiệu năng cao cho học tập và giải trí.").build(),
                Product.builder().productName("Tai nghe AirPods Pro 3").category("Điện tử").price(3950000.0).stock(40).description("Tai nghe không dây, pin bền, âm bass mạnh.").build(),
                Product.builder().productName("Áo khoác gió Uniqilo").category("Thời trang").price(620000.0).stock(25).description("Áo khoác chống gió, nhẹ, dễ phối đồ.").build(),
                Product.builder().productName("Giày bóng chuyền Asic").category("Thời trang").price(1350000.0).stock(30).description("Đế êm, phù hợp vận động mạnh.").build(),
                Product.builder().productName("Nồi chiên không dầu 5L").category("Gia dụng").price(1850000.0).stock(18).description("Dung tích lớn, nhiều chế độ nấu.").build(),
                Product.builder().productName("Máy lọc không khí Mini").category("Gia dụng").price(2150000.0).stock(10).description("Lọc bụi mịn, khử mùi, phù hợp phòng nhỏ.").build(),
                Product.builder().productName("Bếp từ đơn SmartCook").category("Gia dụng").price(1980000.0).stock(22).description("Bếp từ nhỏ gọn, tiết kiệm điện.").build(),
                Product.builder().productName("Sạc nhanh 65W PD").category("Điện tử").price(650000.0).stock(55).description("Sạc nhanh chuẩn PD, an toàn cho thiết bị.").build(),
                Product.builder().productName("Áo thun basic cotton").category("Thời trang").price(180000.0).stock(80).description("Chất cotton mềm, thấm hút tốt.").build(),
                Product.builder().productName("Bàn là hơi nước 2200W").category("Gia dụng").price(890000.0).stock(15).description("Làm phẳng nhanh, an toàn cho nhiều loại vải.").build()
        );
        products.forEach(productService::save);
    }

    @ModelAttribute("categories")
    public List<String> categories() {
        return List.of("Điện tử", "Thời trang", "Gia dụng");
    }

    @GetMapping
    public String listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "productName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productService.findAll(keyword, pageable);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword == null ? "" : keyword);

        return "products/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("isEdit", false);
        return "products/form";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "products/form";
        }

        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "productName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String keyword,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không tồn tại.");
            return "redirect:/products";
        }

        model.addAttribute("product", productOptional.get());
        model.addAttribute("isEdit", true);
        model.addAttribute("page", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        return "products/form";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "productName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String keyword,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            model.addAttribute("page", page);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("keyword", keyword == null ? "" : keyword);
            return "products/form";
        }

        product.setId(id);
        productService.save(product);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("sortField", sortField);
        redirectAttributes.addAttribute("sortDir", sortDir);
        if (keyword != null && !keyword.isBlank()) {
            redirectAttributes.addAttribute("keyword", keyword);
        }
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "productName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String keyword,
            RedirectAttributes redirectAttributes
    ) {
        productService.deleteById(id);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("sortField", sortField);
        redirectAttributes.addAttribute("sortDir", sortDir);
        if (keyword != null && !keyword.isBlank()) {
            redirectAttributes.addAttribute("keyword", keyword);
        }
        return "redirect:/products";
    }
}
