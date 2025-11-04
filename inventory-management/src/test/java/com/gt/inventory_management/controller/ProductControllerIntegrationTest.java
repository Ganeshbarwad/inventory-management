package com.gt.inventory_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.inventory_management.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDTO productDTO;

    @BeforeEach
    void setup() {
        productDTO = new ProductDTO();
        productDTO.setName("Laptop");
        productDTO.setCategory("Electronics");
        productDTO.setBrand("Dell");
        productDTO.setPrice(BigDecimal.valueOf(75000));
        productDTO.setQuantity(10);
        productDTO.setMinStockLevel(5);
        productDTO.setDescription("Dell Inspiron Laptop");
    }

    @Test
    void testAddProduct() throws Exception {
        mockMvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products?page=0&size=5"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProduct() throws Exception {
        productDTO.setName("Updated Laptop");

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Laptop"));
    }

    @Test
    void testUpdateStock() throws Exception {
        mockMvc.perform(patch("/api/products/1/stock?quantity=5"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Stock updated successfully")));
    }
}
