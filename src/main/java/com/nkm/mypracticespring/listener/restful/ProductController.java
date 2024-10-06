package com.nkm.mypracticespring.listener.restful;

import com.nkm.mypracticespring.common.context.RestfulCtx;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/drafts/all")
    public ResponseDto<?> getAllDraftsForShop(@RequestParam(name = "page", required = false, defaultValue = "50") Integer page,
                                              @RequestParam(name = "size", required = false, defaultValue = "0") Integer size) {
        return new ResponseDto<>(productService.findAllDraftsForShop(RestfulCtx.shopContext().id(), page, size));
    }

    @PutMapping("/publish/{productId}")
    public ResponseDto<?> publishProduct(@PathVariable("productId") String productId) {
        return new ResponseDto<>(productService.publishProduct(productId, RestfulCtx.shopContext().id()));
    }

    @GetMapping("/published/all")
    public ResponseDto<?> getAllPublishedForShop(@RequestParam(name = "limit", required = false, defaultValue = "50") Integer limit,
                                                 @RequestParam(name = "skip", required = false, defaultValue = "0") Integer skip) {
        return new ResponseDto<>(productService.getAllPublishedForShop(RestfulCtx.shopContext().id(), limit, skip));
    }

}
