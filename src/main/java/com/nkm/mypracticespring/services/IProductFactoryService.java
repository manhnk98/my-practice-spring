package com.nkm.mypracticespring.services;

public interface IProductFactoryService {

    void createProduct(Object payload);

    void updateProduct(String productId, Object payload);

}
