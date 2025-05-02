package com.example.demotekton.service.impl;

import com.example.demotekton.entity.CallLog;
import com.example.demotekton.repository.CallLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CallLogServiceImplTest {


    @Mock
    private CallLogRepository callLogRepository;

    @InjectMocks
    private CallLogServiceImpl callLogService;

    @Test
    void testLog_shouldCallRepositorySave() {
        CallLog callLog = new CallLog(); // O con constructor/mock

        callLogService.log(callLog);

        verify(callLogRepository, times(1)).save(callLog);
    }

    @Test
    void testGetAllCallLogs_shouldReturnListFromRepository() {
        List<CallLog> expectedLogs = List.of(new CallLog(), new CallLog());
        when(callLogRepository.findAll()).thenReturn(expectedLogs);

        List<CallLog> actualLogs = callLogService.getAllCallLogs();

        assertEquals(expectedLogs, actualLogs);
        verify(callLogRepository).findAll();
    }

}