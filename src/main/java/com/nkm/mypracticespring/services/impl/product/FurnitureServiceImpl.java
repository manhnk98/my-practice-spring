package com.nkm.mypracticespring.services.impl.product;

import com.nkm.mypracticespring.dto.product.FurniturePayload;
import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.dto.product.ProductUpdateRequest;
import com.nkm.mypracticespring.models.FurnitureModel;
import com.nkm.mypracticespring.repositories.IFurnitureRepository;
import com.nkm.mypracticespring.services.ProductFactoryService;
import com.nkm.mypracticespring.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
public class FurnitureServiceImpl extends ProductFactoryService {

    @Autowired
    private IFurnitureRepository furnitureRepository;


    @Override
    public String createProduct(String shopId, ProductCreateRequest request) {
        FurniturePayload payload = JsonUtils.toObject(request.getProductAttributes(), FurniturePayload.class);
        FurnitureModel model = new FurnitureModel();
        model.setCreatedAt(LocalDateTime.now());
        model.setUpdatedAt(LocalDateTime.now());

        model.setBrand(payload.getBrand());
        model.setSize(payload.getSize());
        model.setMaterial(payload.getMaterial());
        furnitureRepository.save(model);

        return super.createProduct(request, shopId, model.getId());
    }

    @Override
    public void updateProduct(String shopId, String productId, ProductUpdateRequest request) {

    }
}
