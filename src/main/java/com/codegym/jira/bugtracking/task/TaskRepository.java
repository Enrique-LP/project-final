package com.codegym.jira.bugtracking.task;

import com.codegym.jira.common.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface TaskRepository extends BaseRepository<Task> {
    @Query("SELECT t FROM Task t WHERE t.sprintId =:sprintId ORDER BY t.startpoint DESC")
    List<Task> findAllBySprintId(long sprintId);

    @Query("SELECT t FROM Task t WHERE t.projectId =:projectId AND t.sprintId IS NULL")
    List<Task> findAllByProjectIdAndSprintIsNull(long projectId);

    @Query("SELECT t FROM Task t WHERE t.projectId =:projectId ORDER BY t.startpoint DESC")
    List<Task> findAllByProjectId(long projectId);

    @Query("SELECT t FROM Task t JOIN FETCH t.project LEFT JOIN FETCH t.sprint LEFT JOIN FETCH t.parent WHERE t.id =:id")
    Optional<Task> findFullById(long id);

    @Modifying
    @Query("UPDATE Task t SET t.sprintId = :sprintId WHERE t.id = :taskId OR t.parentId = :taskId")
    void setTaskAndSubTasksSprint(long taskId, Long sprintId);
}
