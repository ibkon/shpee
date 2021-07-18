package com.xentn.shpee.bean.product;

import lombok.Data;
/***
 * @Description: TODO
 *
 * @author: ibkon
 * @date: 2021/7/17
 * @version: 1.0
 */
@Data
public class TProductParameter {
    private String ProductParameterId;
    private String ProductParameterNextId;
    private boolean KeyItem;
    private String ProductParameterText;
}
