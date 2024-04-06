package edu.ntnu.fullstack.prosjekt.quizzer.repositories;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.CategoryEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * Provides basic CRUD functionality for database operations against the quiz database table.
 */
@Repository
public interface QuizRepository extends CrudRepository<QuizEntity, Long>,
        PagingAndSortingRepository<QuizEntity, Long> {

  //Page<QuizEntity> findQuizEntitiesByCategories(Set<CategoryEntity> categories, Pageable pageable);

  @Query("SELECT entity FROM QuizEntity entity JOIN entity.categories category WHERE category.categoryName LIKE %:searchQuery% OR entity.title LIKE %:searchQuery%")
  Page<QuizEntity> findByCategoriesInOrTitleContaining(@RequestParam("searchQuery") String searchQuery, Pageable pageable);
}
