package edu.ntnu.fullstack.prosjekt.quizzer.mappers.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import org.modelmapper.ModelMapper;

public class QuizMapperImpl implements Mapper<QuizEntity, QuizDto> {

    private ModelMapper modelMapper;

    public QuizMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public QuizDto mapTo(QuizEntity quizEntity) {
        return modelMapper.map(quizEntity, QuizDto.class);
    }

    @Override
    public QuizEntity mapFrom(QuizDto quizDto) {
        return modelMapper.map(quizDto, QuizEntity.class);
    }
}
