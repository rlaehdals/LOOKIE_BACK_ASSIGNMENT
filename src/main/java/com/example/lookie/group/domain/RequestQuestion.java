package com.example.lookie.group.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class RequestQuestion {

    @Id @GeneratedValue
    @Column(name = "requestQuestion_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "groupRequest_id")
    private GroupRequest groupRequest;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private String answer;

    public static RequestQuestion createRequestQuestion(Question question, String answer){
        RequestQuestion requestQuestion = new RequestQuestion();
        requestQuestion.question = question;
        requestQuestion.answer = answer;
        return requestQuestion;
    }
}
