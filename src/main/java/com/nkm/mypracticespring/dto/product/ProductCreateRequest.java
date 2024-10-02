package com.nkm.mypracticespring.dto.product;

import com.nkm.mypracticespring.dto.common.DataRequest;
import com.nkm.mypracticespring.enums.ProductTypeEnum;
import com.nkm.mypracticespring.exceptions.DataRequestInvalidException;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductCreateRequest extends DataRequest {

    private String productName;

    private String productThumb;

    private String productDescription;

    private String productSlug;

    private Double productPrice;

    private Integer productQuality;

    private String productType;

    private Map<String, Object> productAttributes;

    private Double productRatingsAverage;

    private List<String> productVariations;

    @Override
    public void validate() throws DataRequestInvalidException {
        ProductTypeEnum productTypeEnum = ProductTypeEnum.get(this.productType);
        if (productTypeEnum == null) {
            throw new DataRequestInvalidException("Invalid product type: " + this.productType);
        }

        if (this.productAttributes == null || this.productAttributes.isEmpty()) {
            throw new DataRequestInvalidException("Product attributes is not empty");
        }
    }
}
