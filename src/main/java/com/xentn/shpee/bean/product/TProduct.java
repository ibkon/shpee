package com.xentn.shpee.bean.product;

import lombok.Data;

import java.io.Serializable;

/***
 * @Description: product bean
 *
 * @author: ibkon
 * @date: 2021/7/10
 *
 */
@Data
public class TProduct  implements Serializable {
    private String  productId;
    private String  productName;
    private String  productGroupId;
    private String  productLabel;
    private String  productParameterId;
    private boolean enabled = true;
}
