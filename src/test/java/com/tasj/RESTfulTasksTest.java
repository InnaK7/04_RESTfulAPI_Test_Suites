package com.tasj;


import com.tasj.containers.ErrorContainer;
import com.tasj.containers.Task;
import com.tasj.resources.Tasks;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.tasj.core.RESTHelpers.requestTo;
import static com.tasj.resources.Tasks.*;
import static org.junit.Assert.assertEquals;

public class RESTfulTasksTest {

    @Before
    public void reset() {
        authorizedRequest(Tasks.URI + "/reset").method("GET");
    }

    @Test
    public void testUnauthorizedReadTasks() {
        Response response = requestTo(Tasks.URI).get();

        assertEquals(new ErrorContainer("Unauthorized access"), response.readEntity(ErrorContainer.class));
        assertEquals(403, response.getStatus());

    }

    @Test
    public void testReadTasks() {
        List<Task> receivedTasks = Tasks.get();

        assertEquals(2, receivedTasks.size());
        assertEquals(defaultTask1.getTitle(), receivedTasks.get(0).getTitle());
        assertEquals(defaultTask2.getTitle(), receivedTasks.get(1).getTitle());
    }

    @Test
    public void testCreate() {
        Task task = Tasks.create("give lesson");

        assertEquals("give lesson", task.getTitle());
    }

    @Test
    public void testCreateAllFields() {
        Task task = Tasks.create(new Task("title", "description", Tasks.URI + "/3", false));

        assertEquals("title", task.getTitle());
        assertEquals("description", task.getDescription());
        assertEquals(Tasks.URI + "/3", task.getUri());
    }

    @Test
    public void testUpdate() {
        Task task = Tasks.update(Tasks.URI + "/1", new Task("Updated task", "Updated Description"));

        assertTasks(task, defaultTask2);
    }

    @Test
    public void testDelete() {
        Tasks.delete(Tasks.URI + "/1");

        assertTasks(defaultTask2);
    }

    @Test
    public void testCreateUpdateDelete() {
        //Create
        Task taskCreated = Tasks.create(new Task("CRUD", "CRUD description"));
        String taskURI = taskCreated.getUri();

        //Update
        Task taskUpdated = Tasks.update(taskURI, new Task("UD", "UD description"));
        assertTasks(defaultTask1, defaultTask2, taskUpdated);

        //Delete
        Tasks.delete(taskURI);
        assertTasks(defaultTask1, defaultTask2);
    }

}
