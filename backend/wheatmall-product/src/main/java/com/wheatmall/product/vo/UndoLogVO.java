package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * UndoLog VO
 */
@Data
public class UndoLogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long branchId;
    private String xid;
    private String context;
    private byte[] rollbackInfo;
    private Integer logStatus;
    private Date logCreated;
    private Date logModified;
    private String ext;
}
