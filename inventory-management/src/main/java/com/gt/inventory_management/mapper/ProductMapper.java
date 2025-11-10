package com.gt.inventory_management.mapper;

import com.gt.inventory_management.dto.ProductDTO;
import com.gt.inventory_management.model.Product;


public class ProductMapper {

    public static ProductDTO toDTO(Product product){
        ProductDTO dto=new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setDescription(product.getDescription());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setMinStockLevel(product.getMinStockLevel());
        dto.setDealerId(product.getDealerId());
        return dto;
    }

    public static Product toProduct(ProductDTO dto){
        Product product =new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setMinStockLevel(dto.getMinStockLevel());
        product.setDealerId(dto.getDealerId());
        product.setCreatedAt(dto.getCreatedAt());
        product.setUpdatedAt(dto.getUpdatedAt());
        return product;
    }

}
