package com.wheatmall.product.service;

import com.wheatmall.product.entity.UndoLogEntity;
import com.wheatmall.product.vo.UndoLogVO;
import com.wheatmall.product.mapper.UndoLogMapper;
import com.wheatmall.product.service.Impl.UndoLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UndoLogServiceImplTest {

    @Mock
    private UndoLogMapper undoLogMapper;

    @InjectMocks
    private UndoLogServiceImpl undoLogService;

    @Test
    void testSaveBatchNormal() {
        UndoLogVO v1 = new UndoLogVO();
        v1.setId(1L);
        v1.setXid("xid-1");

        UndoLogVO v2 = new UndoLogVO();
        v2.setId(2L);
        v2.setXid("xid-2");

        List<UndoLogVO> list = Arrays.asList(v1, v2);

        BatchResult br = mock(BatchResult.class);
        when(undoLogMapper.insert(anyList())).thenReturn(List.of(br));

        int result = undoLogService.saveBatch(list);
        assertTrue(result > 0, "size > 0 即批量成功");

        ArgumentCaptor<List<UndoLogEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(undoLogMapper).insert(captor.capture());
        assertEquals(2, captor.getValue().size());
        assertEquals("xid-1", captor.getValue().get(0).getXid());
        assertEquals("xid-2", captor.getValue().get(1).getXid());
    }

    @Test
    void testSaveBatchSingle() {
        UndoLogVO v1 = new UndoLogVO();
        v1.setId(1L);

        BatchResult br = mock(BatchResult.class);
        when(undoLogMapper.insert(anyList())).thenReturn(List.of(br));

        int result = undoLogService.saveBatch(Collections.singletonList(v1));
        assertTrue(result > 0);

        verify(undoLogMapper).insert(anyList());
    }

    @Test
    void testSaveBatchEmpty() {
        BatchResult br = mock(BatchResult.class);
        when(undoLogMapper.insert(anyList())).thenReturn(List.of(br));

        int result = undoLogService.saveBatch(Collections.emptyList());
        assertTrue(result > 0);

        verify(undoLogMapper).insert(Collections.emptyList());
    }

    /**
     * 模拟两条全成功：updateCounts = [1, 1]
     */
    @Test
    void testInsertBatchUpdateCountsAllSuccess() {
        UndoLogEntity e1 = new UndoLogEntity();
        e1.setXid("xid-1");
        UndoLogEntity e2 = new UndoLogEntity();
        e2.setXid("xid-2");

        BatchResult br = mock(BatchResult.class);
        when(br.getUpdateCounts()).thenReturn(new int[]{1, 1});

        when(undoLogMapper.insert(anyList())).thenReturn(List.of(br));

        List<BatchResult> results = undoLogMapper.insert(Arrays.asList(e1, e2));

        assertArrayEquals(new int[]{1, 1}, results.get(0).getUpdateCounts());
    }

    /**
     * 模拟一条失败（updateCount = 0 或 -2 = Statement.SUCCESS_NO_INFO）
     * 但实际有 @Transactional，失败即回滚，不会出现此场景
     */
    @Test
    void testInsertBatchOneFailUpdateCounts() {
        UndoLogEntity e1 = new UndoLogEntity();
        e1.setXid("xid-1");
        UndoLogEntity e2 = new UndoLogEntity();
        e2.setXid("xid-2");

        BatchResult br = mock(BatchResult.class);
        // MyBatis BatchExecutor: 失败条目返回 -2 (Statement.EXECUTE_FAILED)
        when(br.getUpdateCounts()).thenReturn(new int[]{1, -2});

        when(undoLogMapper.insert(anyList())).thenReturn(List.of(br));

        List<BatchResult> results = undoLogMapper.insert(Arrays.asList(e1, e2));

        int[] counts = results.get(0).getUpdateCounts();
        assertEquals(1, counts[0]);   // 第一条成功
        assertEquals(-2, counts[1]);  // 第二条失败（Statement.EXECUTE_FAILED）
    }
}
