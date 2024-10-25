package com.nkm.mypracticespring.services.impl.product;

import com.nkm.mypracticespring.dto.product.ElectronicPayload;
import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.dto.product.ProductUpdateRequest;
import com.nkm.mypracticespring.exceptions.AppException;
import com.nkm.mypracticespring.models.ElectronicModel;
import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.models.ShopModel;
import com.nkm.mypracticespring.repositories.IElectronicRepository;
import com.nkm.mypracticespring.services.ProductFactoryService;
import com.nkm.mypracticespring.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
public class ElectronicServiceImpl extends ProductFactoryService {

    @Autowired
    private IElectronicRepository electronicRepository;


    @Override
    public String createProduct(String shopId, ProductCreateRequest request) {
        ElectronicPayload payload = JsonUtils.toObject(request.getProductAttributes(), ElectronicPayload.class);
        ElectronicModel model = new ElectronicModel();
        model.setCreatedAt(LocalDateTime.now());
        model.setUpdatedAt(LocalDateTime.now());

        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);
        model.setProductShop(shopModel);

        assert payload != null;
        model.setManufacturer(payload.getManufacturer());
        model.setModel(payload.getModel());
        model.setColor(payload.getColor());

        electronicRepository.save(model);

        return super.createProduct(request, shopId, model.getId());
    }

    @Override
    public ProductModel updateProduct(String shopId, String productId, ProductUpdateRequest request) {
        ShopModel shopModel = new ShopModel();
        shopModel.setId(shopId);
        ElectronicModel electronicModel = electronicRepository.findFirstByIdAndProductShop(productId, shopModel);
        if (electronicModel == null) {
            throw new AppException("product not found");
        }

        if (request.getProductAttributes() != null && !request.getProductAttributes().isEmpty()) {
            ElectronicPayload payload = JsonUtils.toObject(request.getProductAttributes(), ElectronicPayload.class);
            if (payload != null) {
                electronicModel.setManufacturer(payload.getManufacturer());
                electronicModel.setModel(payload.getModel());
                electronicModel.setColor(payload.getColor());
                electronicModel.setUpdatedAt(LocalDateTime.now());
                electronicRepository.save(electronicModel);
            }
        }

        return super.update(shopId, productId, request);
    }
}
