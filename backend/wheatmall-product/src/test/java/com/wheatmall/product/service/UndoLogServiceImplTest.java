package com.wheatmall.product.service;

import com.wheatmall.product.entity.UndoLogEntity;
import com.wheatmall.product.mapper.UndoLogMapper;
import com.wheatmall.product.service.Impl.UndoLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UndoLogServiceImplTest {

    @Mock
    private UndoLogMapper undoLogMapper;

    @InjectMocks
    private UndoLogServiceImpl undoLogService;

    @Test
    void testSaveBatchNormal() {
        UndoLogEntity e1 = new UndoLogEntity();
        e1.setId(1L);
        e1.setXid("xid-1");

        UndoLogEntity e2 = new UndoLogEntity();
        e2.setId(2L);
        e2.setXid("xid-2");

        List<UndoLogEntity> list = Arrays.asList(e1, e2);

        undoLogService.saveBatch(list);

        ArgumentCaptor<List<UndoLogEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(undoLogMapper).insert(captor.capture());
        assertEquals(2, captor.getValue().size());
        assertEquals("xid-1", captor.getValue().get(0).getXid());
        assertEquals("xid-2", captor.getValue().get(1).getXid());
    }

    @Test
    void testSaveBatchSingle() {
        UndoLogEntity e1 = new UndoLogEntity();
        e1.setId(1L);

        undoLogService.saveBatch(Collections.singletonList(e1));

        verify(undoLogMapper).insert(Collections.singletonList(e1));
    }

    @Test
    void testSaveBatchEmpty() {
        undoLogService.saveBatch(Collections.emptyList());

        verify(undoLogMapper).insert(Collections.emptyList());
    }
}
