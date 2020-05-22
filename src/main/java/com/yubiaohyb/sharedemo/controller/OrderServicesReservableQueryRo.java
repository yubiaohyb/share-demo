package com.yubiaohyb.sharedemo.controller;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/5/8 14:04
 */
@Data
public class OrderServicesReservableQueryRo implements Serializable {
    private List<String> taobaoServiceSku;

    private Long taobaoStoreId;

    private String telephone;
}
