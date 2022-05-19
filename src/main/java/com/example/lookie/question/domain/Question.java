package com.example.lookie.question.domain;

import com.example.lookie.group.domain.Group;
import com.example.lookie.questionAnswer.QuestionAnswer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private List<QuestionAnswer> questionAnswerList = new ArrayList<>();

    // 연관관계 메서드
    public void setGroup(Group group){
        this.group=group;
        group.addQuestion(this);
    }

    public void addQuestionAnswer(QuestionAnswer questionAnswer){
        this.questionAnswerList.add(questionAnswer);
    }

    // 생성 메서드
    public static Question createQuestion(Group group, String title) {
        Question question = new Question();
        question.setGroup(group);
        question.title = title;
        return question;
    }

    public void changeTitle(String changeTitle) {
        this.title=changeTitle;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        Question obj1 = (Question) obj;
        return this.id.equals(obj1.id);
    }
}
