package com.example.app.ksugym;

public class Classes
{
    String className, trainerName, classDescription, classStudentsNum;

    public Classes(String className, String trainerName, String classDescription, String classStudentsNum) {
        this.className = className;
        this.trainerName = trainerName;
        this.classDescription = classDescription;
        this.classStudentsNum = classStudentsNum;
    }

    public Classes() {
    }

    public String getClassName() {
        return className;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public String getClassStudentsNum() {
        return classStudentsNum;
    }
}
