package com.example.Technical_Assessment_Assignment.Service;

import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class MockService {

    private static final Map<Integer, String> userIdToName = Map.of(
            1001, "reza",
            1002, "ali",
            1003, "morteza"
    );

    private static final Map<String, Integer> emailToUserId = Map.of(
            "reza@example.com", 1001,
            "ali@example.com", 1002,
            "morteza@example.com", 1003
    );

    public Integer getUserIdByEmail(String email) {
        return emailToUserId.getOrDefault(email, 0);
    }

    public String getUserNameById(Integer userId) {
        return userIdToName.getOrDefault(userId, "UNKNOWN_USER");
    }
}

