package com.nkm.mypracticespring.listener.restful;

import com.nkm.mypracticespring.common.context.RestfulCtx;
import com.nkm.mypracticespring.dto.common.ResponseDto;
import com.nkm.mypracticespring.dto.common.ResponseList;
import com.nkm.mypracticespring.dto.product.ProductCreateRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.models.ProductModel;
import com.nkm.mypracticespring.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseDto<?> findAllProducts(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                          @RequestParam(name = "sort", required = false, defaultValue = "ctime") String sort) {
        Page<ProductModel> pageProduct = productService.findAllProducts(RestfulCtx.shopContext().id(), page, size, sort);
        ResponseList<?> response = new ResponseList<>(pageProduct.getContent(), page, size,
                pageProduct.getTotalPages(), pageProduct.getTotalElements());

        return new ResponseDto<>(response);
    }

    @GetMapping("/{productId}")
    public ResponseDto<?> findProduct(@PathVariable("productId") String productId) {
        ProductModel response = productService.findOneProduct(RestfulCtx.shopContext().id(), productId);

        return new ResponseDto<>(response);
    }

    @GetMapping("/search")
    public ResponseDto<?> searchProducts(@RequestParam("keySearch") String keySearch,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        Page<ProductModel> pageProduct = productService.searchProduct(RestfulCtx.shopContext().id(), keySearch, page, size);
        ResponseList<?> response = new ResponseList<>(pageProduct.getContent(), page, size,
                pageProduct.getTotalPages(), pageProduct.getTotalElements());

        return new ResponseDto<>(response);
    }

    @GetMapping("/drafts/all")
    public ResponseDto<?> getAllDraftsForShop(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        Page<ProductModel> pageProduct = productService.findAllDraftsForShop(RestfulCtx.shopContext().id(), page, size);
        ResponseList<?> response = new ResponseList<>(pageProduct.getContent(), page, size,
                pageProduct.getTotalPages(), pageProduct.getTotalElements());

        return new ResponseDto<>(response);
    }

    @PutMapping("/publish/{productId}")
    public ResponseDto<?> publishProduct(@PathVariable("productId") String productId) {
        return new ResponseDto<>(productService.publishProduct(productId, RestfulCtx.shopContext().id()));
    }

    @PutMapping("/unpublish/{productId}")
    public ResponseDto<?> unpublishProduct(@PathVariable("productId") String productId) {
        return new ResponseDto<>(productService.unpublishProduct(productId, RestfulCtx.shopContext().id()));
    }

    @GetMapping("/published/all")
    public ResponseDto<?> getAllPublishedForShop(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        Page<ProductModel> pageProduct = productService.getAllPublishedForShop(RestfulCtx.shopContext().id(), page, size);
        ResponseList<?> response = new ResponseList<>(pageProduct.getContent(), page, size,
                pageProduct.getTotalPages(), pageProduct.getTotalElements());

        return new ResponseDto<>(response);
    }

}
