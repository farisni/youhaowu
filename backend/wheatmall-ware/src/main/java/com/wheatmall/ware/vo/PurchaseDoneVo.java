package com.wheatmall.ware.vo;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class PurchaseDoneVo {
    @NotNull(message = "id不允许为空")
    private Long id;

    private List<PurchaseItemDoneVo> items;
}
