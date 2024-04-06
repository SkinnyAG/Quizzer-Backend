package edu.ntnu.fullstack.prosjekt.quizzer.mappers.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.CategoryDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.CategoryEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements Mapper<CategoryEntity, CategoryDto> {
  ModelMapper modelMapper;

  public CategoryMapperImpl(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public CategoryDto mapTo(CategoryEntity categoryEntity) {
    return modelMapper.map(categoryEntity, CategoryDto.class);
  }

  @Override
  public CategoryEntity mapFrom(CategoryDto categoryDto) {
    return modelMapper.map(categoryDto, CategoryEntity.class);
  }
}
