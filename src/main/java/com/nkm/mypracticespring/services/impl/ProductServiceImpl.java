package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.services.IProductFactoryService;
import com.nkm.mypracticespring.services.IProductService;
import com.nkm.mypracticespring.services.impl.product.ClothesServiceImpl;
import com.nkm.mypracticespring.services.impl.product.ElectronicServiceImpl;
import com.nkm.mypracticespring.services.impl.product.FurnitureServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class ProductServiceImpl implements IProductService {

    private static final Map<ProductTypeEnum, IProductFactoryService> PRODUCT_FACTORY_REGISTRY = new HashMap<>();

    public ProductServiceImpl(ClothesServiceImpl clothesService,
                              ElectronicServiceImpl electronicService,
                              FurnitureServiceImpl furnitureService) {
        PRODUCT_FACTORY_REGISTRY.put(ProductTypeEnum.Clothes, clothesService);
        PRODUCT_FACTORY_REGISTRY.put(ProductTypeEnum.Electronic, electronicService);
        PRODUCT_FACTORY_REGISTRY.put(ProductTypeEnum.Furniture, furnitureService);
    }

    @Override
    public void createProduct(ProductTypeEnum productType, Object payload) {
        IProductFactoryService productFactory = PRODUCT_FACTORY_REGISTRY.get(productType);
        productFactory.createProduct("");
    }

    @Override
    public void updateProduct(ProductTypeEnum productType, Object payload) {
        IProductFactoryService productFactory = PRODUCT_FACTORY_REGISTRY.get(productType);
        productFactory.updateProduct("", "");
    }
}
