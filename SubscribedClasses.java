package com.example.app.ksugym.Students;

public class SubscribedClasses
{
    String classname, trainername;

    public SubscribedClasses(String className, String trainerName) {
        this.classname = className;
        this.trainername = trainerName;
    }

    public SubscribedClasses() {
    }

    public String getClassName() {
        return classname;
    }

    public String getTrainerName() {
        return trainername;
    }
}
