package edu.ntnu.fullstack.prosjekt.quizzer.mappers.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapperImpl implements Mapper<QuestionEntity, QuestionDto> {

    private ModelMapper modelMapper;

    public QuestionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public QuestionDto mapTo(QuestionEntity questionEntity) {
        return modelMapper.map(questionEntity, QuestionDto.class);
    }

    @Override
    public QuestionEntity mapFrom(QuestionDto questionDto) {
        return modelMapper.map(questionDto, QuestionEntity.class);
    }
}
