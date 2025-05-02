package com.example.demotekton.service;

import com.example.demotekton.entity.CallLog;

import java.util.List;

public interface CallLogService {

    void log(CallLog callLog);

    List<CallLog> getAllCallLogs();

}
