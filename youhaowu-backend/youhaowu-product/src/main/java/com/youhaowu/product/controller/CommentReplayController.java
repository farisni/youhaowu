package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.product.service.CommentReplayService;
import com.youhaowu.product.query.CommentReplayQueryDTO;
import com.youhaowu.product.vo.CommentReplayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品评价回复控制器
 */
@RestController
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
