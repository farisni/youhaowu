package com.youhaowu.seckill.controller;

import com.youhaowu.common.utils.R;
import com.youhaowu.common.vo.seckill.SeckillSkuRedisTO;
import com.youhaowu.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 秒杀接口
 * 提供：当前秒杀商品查询、单品秒杀信息、秒杀下单
 */
@RestController
@RequestMapping("/api/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 查询当前时间可参与的秒杀商品列表
     */
    @GetMapping("/current")
    public R<List<SeckillSkuRedisTO>> getCurrentSeckillSkus() {
        List<SeckillSkuRedisTO> results = seckillService.getCurrentSeckillSkus();
        return R.ok(results);
    }

    /**
     * 根据 skuId 查询商品当前时间秒杀信息
     */
    @GetMapping("/sku/{skuId}")
    public R<SeckillSkuRedisTO> getSkuSeckilInfo(@PathVariable("skuId") Long skuId) {
        SeckillSkuRedisTO to = seckillService.getSkuSeckilInfo(skuId);
        return R.ok(to);
    }

    /**
     * 秒杀下单
     * @param killId sessionId_skuid
     * @param key    随机码
     * @param num    购买数量
     */
    @GetMapping("/kill")
    public R<String> kill(@RequestParam("killId") String killId,
                           @RequestParam("key") String key,
                           @RequestParam("num") Integer num) {
        try {
            String orderSn = seckillService.kill(killId, key, num);
            if (orderSn != null) {
                return R.ok(orderSn);
            }
            return R.fail("秒杀失败");
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
