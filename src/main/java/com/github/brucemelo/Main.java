package com.github.brucemelo;

import com.github.brucemelo.infrastructure.AppJPATransaction;
import com.github.brucemelo.model.Student;
import com.querydsl.jpa.impl.JPAQuery;

import static com.github.brucemelo.model.QStudent.student;


public class Main {

    public static void main(String[] args) {

        AppJPATransaction.inTransaction(entityManager -> {
            var student = Student.newStudent("Bruce Melo");
            entityManager.persist(student);
        });

        var catharine = AppJPATransaction.inTransaction(entityManager -> {
            var student = Student.newStudent("Catharine");
            entityManager.persist(student);
            return student;
        });

        System.out.println(catharine);

        AppJPATransaction.inTransaction(entityManager -> {
            var query = new JPAQuery<Student>(entityManager);
            var students = query.from(student).fetch();
            students.forEach(student -> System.out.println(student.getName()));
        });

        AppJPATransaction.inTransaction(entityManager -> {
            var query = new JPAQuery<Student>(entityManager);
            var student1 = query.from(student).where(student.name.eq("Bruce Melo"))
                    .fetchOne();
            System.out.println(student1.getName());
        });

        AppJPATransaction.inTransaction(entityManager -> {
            var query = new JPAQuery<Student>(entityManager);
            var studentId = query.select(student.id).from(student)
                    .where(student.name.eq("Catharine"))
                    .fetchOne();
            System.out.println(studentId);
        });

    }

}