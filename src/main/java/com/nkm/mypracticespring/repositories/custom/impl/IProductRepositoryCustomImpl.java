package com.nkm.mypracticespring.repositories.custom.impl;

import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.repositories.custom.IProductRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IProductRepositoryCustomImpl implements IProductRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<ProductModel> getAllDraftsForShop(String shopId, Pageable pageable) {
        // old
//        Query query = new Query();
//        query.addCriteria(Criteria.where("product_shop.$id").is(new ObjectId(shopId)));
//        query.addCriteria(Criteria.where("is_draft").is(true));
//
//        query.with(Sort.by(Sort.Direction.ASC, "updated_at"))
//                .skip(skip)
//                .limit(limit);
//        return mongoTemplate.find(query, ProductModel.class);
        // end
        Query query = new Query();
        query.addCriteria(Criteria.where("product_shop.$id").is(new ObjectId(shopId)));
        query.addCriteria(Criteria.where("is_draft").is(true));
        query.with(pageable);

        List<ProductModel> products = mongoTemplate.find(query, ProductModel.class);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), ProductModel.class);

        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public Page<ProductModel> getAllPublishedForShop(String shopId, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("product_shop.$id").is(new ObjectId(shopId)));
        query.addCriteria(Criteria.where("is_published").is(true));
        query.with(pageable);

        List<ProductModel> products = mongoTemplate.find(query, ProductModel.class);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), ProductModel.class);

        return new PageImpl<>(products, pageable, total);
    }
}
