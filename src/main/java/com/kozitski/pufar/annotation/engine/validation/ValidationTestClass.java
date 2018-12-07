package com.kozitski.pufar.annotation.engine.validation;

import com.kozitski.pufar.annotation.engine.validation.entity.Student;
import com.kozitski.pufar.annotation.engine.validation.entity.Validatable;

public class ValidationTestClass {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        // на Optional за менить
        Validatable student = Student.createStudent("Andrei", "Kozitski");

        System.out.println(student);

    }

}
