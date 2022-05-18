package com.example.lookie.grouprequest.domain;


import com.example.lookie.group.domain.Group;
import com.example.lookie.questionAnswer.QuestionAnswer;
import com.example.lookie.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus requestStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "department")
    private Department department;

    @OneToMany(mappedBy = "group_request",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<QuestionAnswer> questionAnswerList = new ArrayList<>();


    // 연관관계 메서드
    public void setGroup(Group group){
        this.group=group;
        group.addGroupRequest(this);
    }

    public void setMember(Member member){
        this.member=member;
        member.addGroupRequest(this);
    }
    public void addQuestionAnswer(QuestionAnswer questionAnswer){
        this.questionAnswerList.add(questionAnswer);
        questionAnswer.setGroupRequest(this);
    }

    // 생성 메서드
    public static GroupRequest createGroupRequest(Member member, Group group,
                                                  Department department, QuestionAnswer... questionAnswers){
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.requestStatus=RequestStatus.HOLDING;
        groupRequest.department=department;
        groupRequest.setGroup(group);
        groupRequest.setMember(member);
        for(QuestionAnswer questionAnswer: questionAnswers){
            groupRequest.addQuestionAnswer(questionAnswer);
        }
        return groupRequest;
    }


    // 비즈니스 로직
    public void changeRequestStatus(RequestStatus requestStatus){
        this.requestStatus=requestStatus;
    }
}
