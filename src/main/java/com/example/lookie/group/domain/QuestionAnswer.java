package com.example.lookie.group.domain;

import com.example.lookie.grouprequest.domain.GroupRequest;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class QuestionAnswer {

    @Id @GeneratedValue
    @Column(name = "question_answer_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "groupRequest_id")
    private GroupRequest groupRequest;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "question_title")
    private String title;
    private String answer;

    public static QuestionAnswer createRequestQuestion(Question question, String answer, String title){
        QuestionAnswer requestQuestion = new QuestionAnswer();
        requestQuestion.setQuestion(question);
        requestQuestion.answer = answer;
        requestQuestion.title=title;
        return requestQuestion;
    }

    public void setGroupRequest(GroupRequest groupRequest){
        this.groupRequest=groupRequest;
    }
    public void setQuestion(Question question){
        this.question=question;
        question.addQuestionAnswer(this);
    }
}
