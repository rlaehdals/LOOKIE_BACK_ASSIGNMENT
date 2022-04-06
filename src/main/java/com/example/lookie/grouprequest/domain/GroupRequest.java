package com.example.lookie.grouprequest.domain;


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

    @OneToMany(mappedBy = "group_request",cascade = CascadeType.ALL)
    private List<RequestQuestion> requestQuestionList = new ArrayList<>();

    public static GroupRequest createGroupRequest(Member member, Group group,
                                                  RequestQuestion... requestQuestions,
                                                  Department department){
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.member=member;
        groupRequest.group=group;
        groupRequest.requestStatus=RequestStatus.HOLDING;
        groupRequest.department=department;
        for(RequestQuestion requestQuestion: requestQuestions){
            groupRequest.requestQuestionList.add(requestQuestion);
        }
        return groupRequest;
    }

    public void changeRequestStatus(RequestStatus requestStatus){
        this.requestStatus=requestStatus;
    }
}
