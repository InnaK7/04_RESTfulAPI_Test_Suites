package com.tasj.resources;

import com.tasj.containers.Task;
import com.tasj.containers.TaskContainer;
import com.tasj.containers.TasksContainer;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.tasj.core.RESTHelpers.authorized;
import static com.tasj.core.RESTHelpers.requestTo;
import static org.junit.Assert.assertEquals;

public class Tasks {

    public static String URI = "http://localhost:5000/todo/api/v1.0/tasks";

    public static Task defaultTask1 = new Task("Buy groceries", "Milk, Cheese, Pizza, Fruit, Tylenol", URI + "/1", false);
    public static Task defaultTask2 = new Task("Learn Python", "Need to find a good Python tutorial on the web", URI + "/2", false);

    public static Invocation.Builder authorizedRequest(String uri) {
        return authorized("miguel:python", requestTo(uri));
    }

    public  static List<Task> get() {
        Response response = authorizedRequest(URI).get();
        assertEquals(200, response.getStatus());
        return response.readEntity(TasksContainer.class).getTasks();

    }

    public static Task create(Task task) {
        Response response = authorizedRequest(URI).post(Entity.entity(task, MediaType.APPLICATION_JSON));
        assertEquals(201, response.getStatus());
        return response.readEntity(TaskContainer.class).getTask();
    }

    public static Task create(String taskTitle) {
        return create(new Task(taskTitle));
    }

    public static Task update(String taskURI, Task task) {
        Response response = authorizedRequest(taskURI).put(Entity.entity(task, MediaType.APPLICATION_JSON));
        assertEquals(200, response.getStatus());
        return response.readEntity(TaskContainer.class).getTask();
    }

    public static void delete(String taskURI) {
        Response response = authorizedRequest(taskURI).delete();
        assertEquals(200, response.getStatus());
    }

    public static void assertTasksTitles(String... taskTitles) {
        List<Task> tasks = get();
        assertEquals(taskTitles.length, tasks.size());

        for(int i = 0; i < tasks.size(); i++) {
            assertEquals(taskTitles[i], tasks.get(i).getTitle());
        }
    }

    public static void assertTasks(Task... tasksToCheck) {
        List<Task> tasks = get();
        assertEquals(tasksToCheck.length, tasks.size());

        for(int i = 0; i < tasks.size(); i++) {
            assertEquals(tasksToCheck[i].getTitle(), tasks.get(i).getTitle());
            assertEquals(tasksToCheck[i].getDescription(), tasks.get(i).getDescription());
            assertEquals(tasksToCheck[i].getUri(), tasks.get(i).getUri());
            assertEquals(tasksToCheck[i].isDone(), tasks.get(i).isDone());
        }
    }
}
