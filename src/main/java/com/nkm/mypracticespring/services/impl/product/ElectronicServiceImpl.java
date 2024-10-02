package com.nkm.mypracticespring.services.impl.product;

import com.nkm.mypracticespring.dto.product.ElectronicPayload;
import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.models.ElectronicModel;
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
        model.setCreatedTime(LocalDateTime.now());
        model.setUpdatedTime(LocalDateTime.now());

        model.setManufacturer(payload.getManufacturer());
        model.setModel(payload.getModel());
        model.setColor(payload.getColor());
        electronicRepository.save(model);

        return super.createProduct(request, shopId, model.getId());
    }

    @Override
    public void updateProduct(String productId, ProductCreateRequest request) {

    }
}
