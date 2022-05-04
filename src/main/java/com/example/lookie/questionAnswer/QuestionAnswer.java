package com.example.lookie.questionAnswer;

import com.example.lookie.grouprequest.domain.GroupRequest;
import com.example.lookie.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Column(name = "answer")
    private String answer;


    // 연관관계 메서드
    public void setGroupRequest(GroupRequest groupRequest){
        this.groupRequest=groupRequest;
    }

    public void setQuestion(Question question){
        this.question=question;
        question.addQuestionAnswer(this);
    }

    // 생성 메서드
    public static QuestionAnswer createRequestQuestion(Question question, String answer, String title){
        QuestionAnswer requestQuestion = new QuestionAnswer();
        requestQuestion.setQuestion(question);
        requestQuestion.answer = answer;
        requestQuestion.title=title;
        return requestQuestion;
    }
}
