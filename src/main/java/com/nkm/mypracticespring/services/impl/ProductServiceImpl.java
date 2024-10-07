package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.ProductRepository;
import com.nkm.mypracticespring.services.IProductService;
import com.nkm.mypracticespring.services.ProductFactoryService;
import com.nkm.mypracticespring.services.impl.product.ClothesServiceImpl;
import com.nkm.mypracticespring.services.impl.product.ElectronicServiceImpl;
import com.nkm.mypracticespring.services.impl.product.FurnitureServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

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
    public Page<ProductModel> searchProduct(String shopId, String keySearch, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "score");
        return productRepository.searchProduct(keySearch, pageable);
    }

    @Override
    public Page<ProductModel> findAllDraftsForShop(String shopId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updated_at");
        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);

        return productRepository.findAllByProductShopAndIsDraftIsTrue(shopModel, pageable);
//        return productRepository.getAllDraftsForShop(shopId, pageable);
    }

    @Override
    public Object publishProduct(String productId, String shopId) {
        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);

        Optional<ProductModel> productModel = productRepository.findByIdAndProductShop(productId, shopModel);
        if (productModel.isEmpty()) {
            throw new AppException("Not found product");
        }

        ProductModel product = productModel.get();
        product.setIsDraft(false);
        product.setIsPublished(true);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return product;
    }

    @Override
    public Object unpublishProduct(String productId, String shopId) {
        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);

        Optional<ProductModel> productModel = productRepository.findByIdAndProductShop(productId, shopModel);
        if (productModel.isEmpty()) {
            throw new AppException("Not found product");
        }

        ProductModel product = productModel.get();
        product.setIsDraft(true);
        product.setIsPublished(false);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return product;
    }

    @Override
    public Page<ProductModel> getAllPublishedForShop(String shopId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updated_at");
        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);

        return productRepository.findAllByProductShopAndIsPublishedIsTrue(shopModel, pageable);
//        return productRepository.getAllPublishedForShop(shopId, pageable);
    }
}
