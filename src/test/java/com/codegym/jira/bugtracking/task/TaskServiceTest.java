package com.codegym.jira.bugtracking.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static com.codegym.jira.bugtracking.task.TaskTestData.TASK1_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class TaskServiceTest {

    @Autowired
    private TaskService taskService;    @Test
    void getDevTimeTest() {
        Duration devTime = taskService.getDevTime(TASK1_ID);
        // La tarea estuvo en desarrollo desde 2025-05-20 10:00:00 hasta 2025-05-21 15:30:00
        assertEquals(29, devTime.toHours()); // 29.5 horas aproximadamente
    }

    @Test
    void getTestTimeTest() {
        Duration testTime = taskService.getTestTime(TASK1_ID);
        // La tarea estuvo en pruebas desde 2025-05-21 15:30:00 hasta 2025-05-22 11:45:00
        assertEquals(20, testTime.toHours()); // 20.25 horas aproximadamente
    }

    @Test
    void getDevTimeWhenNoActivitiesTest() {
        Duration devTime = taskService.getDevTime(999L); // ID que no existe
        assertEquals(0, devTime.toSeconds());
    }

    @Test
    void getTestTimeWhenNoActivitiesTest() {
        Duration testTime = taskService.getTestTime(999L); // ID que no existe
        assertEquals(0, testTime.toSeconds());
    }
}