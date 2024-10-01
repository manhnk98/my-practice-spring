package com.nkm.mypracticespring.services;

import org.springframework.stereotype.Service;

@Service
public abstract class ProductFactoryService {

    public abstract void createProduct(Object payload);

    public abstract void updateProduct(String productId, Object payload);

    public String createProduct(String productId) {

        return null;
    }

}
