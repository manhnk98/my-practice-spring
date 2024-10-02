package com.nkm.mypracticespring.listener.restful;

import com.nkm.mypracticespring.common.context.RestfulCtx;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseDto<?> createProduct(@RequestBody ProductCreateRequest request) {
        return new ResponseDto<>(productService.createProduct(
                ProductTypeEnum.get(request.getProductType()), request, RestfulCtx.shopContext().id()));
    }

}
