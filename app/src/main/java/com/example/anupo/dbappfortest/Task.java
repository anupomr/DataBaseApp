package com.example.anupo.dbappfortest;

public class Task {


    private int taskId;
    private String taskName, asaignTo, taskDescription;

    public String getAsaignTo() {
        return asaignTo;
    }

    public void setAsaignTo(String asaignTo) {
        this.asaignTo = asaignTo;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Task(int taskId, String taskName, String asaignTo, String taskDescription) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.asaignTo = asaignTo;
        this.taskDescription = taskDescription;
    }



    public Task()
    {

    }
}
