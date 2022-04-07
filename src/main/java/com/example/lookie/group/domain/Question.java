package com.example.lookie.group.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Question {
    @Id @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private String title;

    @OneToMany(mappedBy = "question")
    private List<QuestionAnswer> questionAnswerList = new ArrayList<>();

    public void setGroup(Group group){
        this.group=group;
        group.addQuestion(this);
    }

    public void addQuestionAnswer(QuestionAnswer questionAnswer){
        this.questionAnswerList.add(questionAnswer);
    }

    public static Question createQuestion(Group group, String title) {
        Question question = new Question();
        question.setGroup(group);
        question.title = title;
        return question;
    }
}
