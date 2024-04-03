package edu.ntnu.fullstack.prosjekt.quizzer.mappers;

public interface Mapper<A,B> {
    B mapTo(A a);

    A mapFrom(B b);
}
