package com.youhaowu.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CommentReplay VO
 */
@Data
public class CommentReplayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long commentId;
    private Long replyId;
}
