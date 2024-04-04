package edu.ntnu.fullstack.prosjekt.quizzer.mappers;

/**
 * An Interface used for defining mappings between DTOs and Entities.
 *
 * @param <A> First class.
 * @param <B> Second class.
 */
public interface Mapper<A, B> {
  /**
   * Takes in an object of Class A and maps it to Class B.
   *
   * @param a Object to convert.
   * @return Converted object.
   */
  B mapTo(A a);

  A mapFrom(B b);
}
