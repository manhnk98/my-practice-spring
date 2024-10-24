package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.dto.product.ProductUpdateRequest;
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
import java.util.*;

@Log4j2
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final Map<ProductTypeEnum, ProductFactoryService> PRODUCT_FACTORY_REGISTER = new HashMap<>();

    public ProductServiceImpl(ClothesServiceImpl clothesService,
                              ElectronicServiceImpl electronicService,
                              FurnitureServiceImpl furnitureService) {
        PRODUCT_FACTORY_REGISTER.put(ProductTypeEnum.Clothes, clothesService);
        PRODUCT_FACTORY_REGISTER.put(ProductTypeEnum.Electronic, electronicService);
        PRODUCT_FACTORY_REGISTER.put(ProductTypeEnum.Furniture, furnitureService);
    }

    @Override
    public String createProduct(ProductTypeEnum productType, ProductCreateRequest request, String shopId) {
        ProductFactoryService productFactory = PRODUCT_FACTORY_REGISTER.get(productType);
        return productFactory.createProduct(shopId, request);
    }

    @Override
    public void updateProduct(String productId, ProductTypeEnum productType, ProductUpdateRequest request, String shopId) {
        ProductFactoryService productFactory = PRODUCT_FACTORY_REGISTER.get(productType);
        if (productFactory == null) {
            throw new AppException("Invalid product Types: " + productType);
        }
        productFactory.updateProduct(shopId, productId, request);
    }

    @Override
    public ProductModel findOneProduct(String shopId, String productId) {
//        ShopModel shopModel = new ShopModel();
//        shopModel.setId(shopId);
//        return productRepository.findByIdAndProductShop(productId, shopModel).orElse(null);

        List<String> fieldsToExclude = List.of("__v", "variations");
        return productRepository.findOneProduct(shopId, productId, fieldsToExclude);
    }

    @Override
    public Page<ProductModel> findAllProducts(String shopId, Integer page, Integer size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ctime".equals(sort) ? Sort.Direction.DESC : Sort.Direction.ASC, "_id"));
        List<String> fieldsSelect = List.of("product_name", "product_price", "product_thumb", "product_shop");

        return productRepository.findAllProducts(shopId, fieldsSelect, pageable);
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
