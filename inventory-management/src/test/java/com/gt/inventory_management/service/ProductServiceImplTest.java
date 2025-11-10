package com.gt.inventory_management.service;

import com.gt.inventory_management.dto.ProductDTO;
import com.gt.inventory_management.exception.ProductNotFoundException;
import com.gt.inventory_management.mapper.ProductMapper;
import com.gt.inventory_management.model.Product;
import com.gt.inventory_management.model.TransactionLog;
import com.gt.inventory_management.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TransactionLogService transactionLogService;

    @InjectMocks
    private ProductServiceimpl productService;

    private Product sampleProduct;
    private ProductDTO sampleDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleProduct = Product.builder()
                .id(1L)
                .name("Laptop")
                .brand("Dell")
                .category("Electronics")
                .price(BigDecimal.valueOf(80000))
                .quantity(10)
                .minStockLevel(5)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        sampleDTO = ProductMapper.toDTO(sampleProduct);
    }

    @Test
    void testAddProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        Product result = productService.addProduct(sampleDTO);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        ProductDTO result = productService.getProductById(1L);

        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(2L));
    }

    @Test
    void testGetAllProducts_NoFilters() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> page = new PageImpl<>(List.of(sampleProduct));

        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<ProductDTO> result = productService.getAllProducts(null, null, null, null, 0, 5);

        assertEquals(1, result.getContent().size());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void testUpdateProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        sampleDTO.setName("Updated Laptop");
        ProductDTO result = productService.updateProduct(1L, sampleDTO);

        assertEquals("Updated Laptop", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
    }

    @Test
    void testUpdateStock_Increase() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        productService.updateStock(1L, 5);

        verify(transactionLogService, times(1)).saveTransaction(any(TransactionLog.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testLowStockAlert_ReturnsLowStock() {
        Product lowStock = Product.builder()
                .id(2L)
                .name("Keyboard")
                .quantity(1)
                .minStockLevel(5)
                .build();

        when(productRepository.findAll()).thenReturn(List.of(sampleProduct, lowStock));

        List<Product> result = productService.lowStockAlert();

        assertEquals(1, result.size());
        assertEquals("Keyboard", result.get(0).getName());
    }

    @Test
    void testLowStockAlert_ThrowsIfNone() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct));

        assertThrows(ProductNotFoundException.class, () -> productService.lowStockAlert());
    }
}
