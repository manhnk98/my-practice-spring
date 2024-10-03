package com.nkm.mypracticespring.repositories.custom.impl;

import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.repositories.custom.IProductRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements IProductRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ProductModel> findAllDraftsForShop(String shopId, int limit, int skip) {
        Query query = new Query();
        query.addCriteria(Criteria.where("productShop.$id").is(shopId));
        query.addCriteria(Criteria.where("isDraft").is(true));

        query.with(Sort.by(Sort.Direction.DESC, "updatedAt"))
                .skip(skip)
                .limit(limit);

        return mongoTemplate.find(new Query(), ProductModel.class);
    }
}
