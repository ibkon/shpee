package com.xentn.shpee.bean.product;

import lombok.Data;

import java.io.Serializable;

/***
 * @Description: TODO
 *
 * @author: ibkon
 * @date: 2021/6/22
 * @version: 1.0
 */
@Data
public class TProductGroup  implements Serializable {
    private String productGroupId;
    private String productGroupName;
}
