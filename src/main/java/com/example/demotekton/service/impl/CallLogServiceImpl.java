package com.example.demotekton.service.impl;


import com.example.demotekton.entity.CallLog;
import com.example.demotekton.repository.CallLogRepository;
import com.example.demotekton.service.CallLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for handling call logs.
 * Provides methods to save and retrieve call log entries using the {@link CallLogRepository}.
 * The save operation is asynchronous to avoid blocking the main execution thread.
 *
 * @author Facundo Cortez
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
public class CallLogServiceImpl implements CallLogService {

    private final CallLogRepository callLogRepository;

    /**
     * Saves a {@link CallLog} entry asynchronously.
     *
     * @param callLog the call log entry to be saved
     */
    @Async
    @Override
    public void log(CallLog callLog) {
        callLogRepository.save(callLog);
    }

    /**
     * Retrieves all stored {@link CallLog} entries.
     *
     * @return a list of all call logs
     */
    @Override
    public List<CallLog> getAllCallLogs() {
        return callLogRepository.findAll();
    }

}
