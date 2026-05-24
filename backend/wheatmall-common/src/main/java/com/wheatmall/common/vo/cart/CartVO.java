package com.wheatmall.common.vo.cart;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

public class CartVO {
    private List<CartItemVO> items;
    private Integer countNum;
    private Integer countType;
    private BigDecimal totalAmount;
    private BigDecimal reduce = new BigDecimal("0.00");

    public List<CartItemVO> getItems() { return items; }
    public void setItems(List<CartItemVO> items) { this.items = items; }

    public Integer getCountNum() {
        int count = 0;
        if (items != null && !items.isEmpty()) {
            for (CartItemVO item : items) count += item.getCount();
        }
        return count;
    }

    public Integer getCountType() {
        return CollectionUtils.isEmpty(items) ? 0 : items.size();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemVO cartItem : items) {
                if (cartItem.getCheck()) amount = amount.add(cartItem.getTotalPrice());
            }
        }
        return amount.subtract(getReduce());
    }

    public BigDecimal getReduce() { return reduce; }
    public void setReduce(BigDecimal reduce) { this.reduce = reduce; }
}
