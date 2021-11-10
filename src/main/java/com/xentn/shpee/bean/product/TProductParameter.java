package com.xentn.shpee.bean.product;

import lombok.Data;

import java.io.Serializable;

/***
 * @Description: TODO
 *
 * @author: ibkon
 * @date: 2021/7/17
 * @version: 1.0
 */
@Data
public class TProductParameter implements Serializable {
    private String ProductParameterId;
    private String ProductParameterNextId;
    private boolean keyItem;
    private String ProductParameterText;
}
