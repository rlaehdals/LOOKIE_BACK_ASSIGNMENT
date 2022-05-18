package com.example.lookie.questionAnswer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerRequestDto {

    public Long questionId;
    public String title;
    public String answer;
}
