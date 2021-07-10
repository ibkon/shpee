package com.xentn.shpee.bean.product;

import lombok.Data;

/***
 * @Description: product bean
 *
 * @author: ibkon
 * @date: 2021/7/10
 *
 */
@Data
public class TProduct {
    private String  productId;
    private String  productName;
    private String  productGroupId;
    private String  productLabel;
    private String  productParameterId;
    private boolean enabled;
}
