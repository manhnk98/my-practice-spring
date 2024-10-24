package com.nkm.mypracticespring.services;

import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.dto.product.ProductUpdateRequest;
import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public abstract class ProductFactoryService {

    @Autowired
    private ProductRepository productRepository;

    public abstract String createProduct(String shopId, ProductCreateRequest request);

    public abstract void updateProduct(String shopId, String productId, ProductUpdateRequest request);

    public String createProduct(ProductCreateRequest request, String shopId, String productId) {
        ProductModel product = new ProductModel();
        product.setId(productId);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        product.setProductName(request.getProductName());
        product.setProductThumb(request.getProductThumb());
        product.setProductDescription(request.getProductDescription());
        product.setProductSlug(request.getProductSlug());
        product.setProductPrice(request.getProductPrice());
        product.setProductQuality(request.getProductQuality());
        product.setProductType(request.getProductType());

        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);
        product.setProductShop(shopModel);

        product.setProductAttributes(request.getProductAttributes());
        product.setProductRatingsAverage(request.getProductRatingsAverage());
        product.setProductVariations(request.getProductVariations());

        productRepository.save(product);
        return product.getId();
    }

    public void updateProduct(String productId, Object bodyUpdate) {

    }

}
