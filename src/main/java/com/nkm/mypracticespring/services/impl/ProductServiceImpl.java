package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.repositories.IProductRepository;
import com.nkm.mypracticespring.services.ProductFactoryService;
import com.nkm.mypracticespring.services.IProductService;
import com.nkm.mypracticespring.services.impl.product.ClothesServiceImpl;
import com.nkm.mypracticespring.services.impl.product.ElectronicServiceImpl;
import com.nkm.mypracticespring.services.impl.product.FurnitureServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    private static final Map<ProductTypeEnum, ProductFactoryService> PRODUCT_FACTORY_REGISTRY = new HashMap<>();

    public ProductServiceImpl(ClothesServiceImpl clothesService,
                              ElectronicServiceImpl electronicService,
                              FurnitureServiceImpl furnitureService) {
        PRODUCT_FACTORY_REGISTRY.put(ProductTypeEnum.Clothes, clothesService);
        PRODUCT_FACTORY_REGISTRY.put(ProductTypeEnum.Electronic, electronicService);
        PRODUCT_FACTORY_REGISTRY.put(ProductTypeEnum.Furniture, furnitureService);
    }

    @Override
    public String createProduct(ProductTypeEnum productType, ProductCreateRequest request, String shopId) {
        ProductFactoryService productFactory = PRODUCT_FACTORY_REGISTRY.get(productType);
        return productFactory.createProduct(shopId, request);
    }

    @Override
    public void updateProduct(ProductTypeEnum productType, ProductCreateRequest request) {
        ProductFactoryService productFactory = PRODUCT_FACTORY_REGISTRY.get(productType);
        productFactory.updateProduct("", request);
    }

    @Override
    public Object findAllDraftsForShop(String shopId, Integer limit, Integer skip) {
        return productRepository.findAllDraftsForShop(shopId, limit, skip);
    }
}
