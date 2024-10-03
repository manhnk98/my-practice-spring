package com.nkm.mypracticespring.services.impl.product;

import com.nkm.mypracticespring.dto.product.ClothesPayload;
import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.models.ClothesModel;
import com.nkm.mypracticespring.repositories.IClothesRepository;
import com.nkm.mypracticespring.services.ProductFactoryService;
import com.nkm.mypracticespring.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
public class ClothesServiceImpl extends ProductFactoryService {

    @Autowired
    private IClothesRepository clothesRepository;

    @Override
    public String createProduct(String shopId, ProductCreateRequest request) {
        ClothesPayload payload = JsonUtils.toObject(request.getProductAttributes(), ClothesPayload.class);
        ClothesModel model = new ClothesModel();
        model.setCreatedAt(LocalDateTime.now());
        model.setUpdatedAt(LocalDateTime.now());

        model.setBrand(payload.getBrand());
        model.setSize(payload.getSize());
        model.setMaterial(payload.getMaterial());
        clothesRepository.save(model);

        return super.createProduct(request, shopId, model.getId());
    }

    @Override
    public void updateProduct(String productId, ProductCreateRequest request) {

    }
}
