package com.nkm.mypracticespring.services.impl;

import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.repositories.IProductRepository;
import com.nkm.mypracticespring.services.ProductFactoryService;
import com.nkm.mypracticespring.services.IProductService;
import com.nkm.mypracticespring.services.impl.product.ClothesServiceImpl;
import com.nkm.mypracticespring.services.impl.product.ElectronicServiceImpl;
import com.nkm.mypracticespring.services.impl.product.FurnitureServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

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

//        Query query = new Query();
//        query.addCriteria(Criteria.where("product_shop.$id").is(new ObjectId(shopId)));
//        query.addCriteria(Criteria.where("is_draft").is(true));
//
//        query.with(Sort.by(Sort.Direction.DESC, "updatedAt"))
//                .skip(skip)
//                .limit(limit);
//
//        List<ProductModel> lstRs = mongoTemplate.find(query, ProductModel.class);
//        return lstRs;

        return productRepository.getAllDraftsForShop(shopId, limit, skip);
    }
}
