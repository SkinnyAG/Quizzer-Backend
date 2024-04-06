package edu.ntnu.fullstack.prosjekt.quizzer.repositories;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.CategoryEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity, String>,
        PagingAndSortingRepository<CategoryEntity, String> {
}
