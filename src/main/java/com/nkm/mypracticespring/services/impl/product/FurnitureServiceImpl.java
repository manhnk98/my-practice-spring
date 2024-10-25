package com.nkm.mypracticespring.services.impl.product;

import com.nkm.mypracticespring.dto.product.FurniturePayload;
import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.dto.product.ProductUpdateRequest;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.models.FurnitureModel;
import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.models.ShopModel;
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

        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);
        model.setProductShop(shopModel);

        assert payload != null;
        model.setBrand(payload.getBrand());
        model.setSize(payload.getSize());
        model.setMaterial(payload.getMaterial());
        furnitureRepository.save(model);

        return super.createProduct(request, shopId, model.getId());
    }

    @Override
    public ProductModel updateProduct(String shopId, String productId, ProductUpdateRequest request) {
        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);
        FurnitureModel model = furnitureRepository.findFirstByIdAndProductShop(productId, shopModel);
        if (model == null) {
            throw new AppException("product not found");
        }

        if (request.getProductAttributes() != null && !request.getProductAttributes().isEmpty()) {
            FurniturePayload payload = JsonUtils.toObject(request.getProductAttributes(), FurniturePayload.class);
            if (payload != null) {
                model.setBrand(payload.getBrand());
                model.setSize(payload.getSize());
                model.setMaterial(payload.getMaterial());
                model.setUpdatedAt(LocalDateTime.now());
                furnitureRepository.save(model);
            }
        }
        return super.update(shopId, productId, request);
    }
}
