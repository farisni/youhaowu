package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.service.CommentReplayService;
import com.wheatmall.product.query.CommentReplayQueryDTO;
import com.wheatmall.product.vo.CommentReplayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品评价回复控制器
 */
@RestController
@RequestMapping(ProductServiceUris.PRODUCT_COMMENT_REPLAY_SERVICE)
public class CommentReplayController {

    @Autowired
    private CommentReplayService commentReplayService;

    /**
     * 分页查询评价回复
     */
    @GetMapping(ProductServiceUris.ProductCommentReplay.PAGE)
    public R<PageData<CommentReplayVO>> list(CommentReplayQueryDTO query) {
        return R.ok(commentReplayService.page(query));
    }
}
