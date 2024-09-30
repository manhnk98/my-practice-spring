package com.nkm.mypracticespring.services.impl.product;

import com.nkm.mypracticespring.repositories.IClothesRepository;
import com.nkm.mypracticespring.services.IProductFactoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ClothesServiceImpl implements IProductFactoryService {

    @Autowired
    private IClothesRepository clothesRepository;

    @Override
    public void createProduct(Object payload) {

    }

    @Override
    public void updateProduct(String productId, Object payload) {

    }
}
