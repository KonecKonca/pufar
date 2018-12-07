package com.kozitski.pufar.annotation.engine.validation.entity;

import com.kozitski.pufar.annotation.engine.validation.annotation.Validate;

    // смогу только для транзитных entity объектов, провекрка онли при их создании
    // @ вещаю на поля в конструкторе создаю объект без валидации, и потом редирект на default метод

public class Student implements Validatable<Student>{
    @Validate(minLength = 5, maxLength = 24)
    private String firstName;
    @Validate(minLength = 5, maxLength = 24, forbiddenValue = "vasia")
    private String lastName;

    public static Student createStudent(String firstName, String lastName){
        Student student = new Student(firstName, lastName);
        return (student.validate(student)) ? student : null;
    }

    private Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return  "firstName: " + firstName +
                ", lastName: " + lastName;
    }

}
