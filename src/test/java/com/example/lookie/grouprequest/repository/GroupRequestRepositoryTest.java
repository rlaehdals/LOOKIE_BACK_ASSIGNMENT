package com.example.lookie.grouprequest.repository;

import com.example.lookie.group.domain.Group;
import com.example.lookie.group.repository.GroupRepository;
import com.example.lookie.grouprequest.domain.Department;
import com.example.lookie.grouprequest.domain.GroupRequest;
import com.example.lookie.grouprequest.domain.RequestStatus;
import com.example.lookie.member.domain.Address;
import com.example.lookie.member.domain.Member;
import com.example.lookie.member.repository.MemberRepository;
import com.example.lookie.questionAnswer.QuestionAnswer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.lookie.grouprequest.domain.Department.FRONT_END;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GroupRequestRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupRequestRepository groupRequestRepository;

    @Test
    @DisplayName("그룹 신청 생성 확인")
    void createGroupRequest() {
        /*
        given
        */
        Member userMember = Member.createUserMember("abc@abc.com", "1234", "ABC"
                , new Address("서울", "종로", "000006"));

        Member userResult = memberRepository.save(userMember);

        Group group = Group.createGroup("루키", "루키입니다", "efg@efg.com");
        Group groupResult = groupRepository.save(group);

        Department department = FRONT_END;
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        QuestionAnswer[] EMPTY = new QuestionAnswer[0];

        GroupRequest groupRequest = GroupRequest.createGroupRequest(userResult, groupResult, department
                , questionAnswerList.toArray(EMPTY));

        /*
        when
         */
        GroupRequest result = groupRequestRepository.save(groupRequest);

        /*
        then
         */
        assertThat(result.getGroup()).isEqualTo(groupRequest.getGroup());
    }

    @Test
    @DisplayName("그룹 신청 리스트 반환 확인")
    void groupRequestList() {
        /*
        given
         */
        Member userMember1 = Member.createUserMember("abc@abc.com", "1234", "ABC",
                new Address("서울", "종로", "000006"));
        Member userMember2 = Member.createUserMember("hij@hij.com", "1234", "HIJ",
                new Address("서울", "종로", "000006"));

        Member adminMember = Member.createAdminMember("efg@efg.com", "1234", "EFG",
                new Address("서울", "종로", "000006"));

        Member userResult1 = memberRepository.save(userMember1);
        Member userResult2 = memberRepository.save(userMember2);

        Member adminResult = memberRepository.save(adminMember);

        Group group = Group.createGroup("루키", "루키입니다", "efg@efg.com");
        Group groupResult = groupRepository.save(group);

        Department department = FRONT_END;
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        QuestionAnswer[] EMPTY = new QuestionAnswer[0];

        GroupRequest groupRequest1 = GroupRequest.createGroupRequest(userResult1, groupResult,
                department, questionAnswerList.toArray(EMPTY));
        GroupRequest groupRequest2 = GroupRequest.createGroupRequest(userResult2, groupResult,
                department, questionAnswerList.toArray(EMPTY));

        groupRequestRepository.save(groupRequest1);
        groupRequestRepository.save(groupRequest2);

        /*
        when
         */
        List<GroupRequest> result = groupRequestRepository.findAll();

        /*
        then
         */
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("그룹 신청 변경 테스트 ")
    void changeGroupRequestStatus() {
        /*
        given
         */
        Member userMember1 = Member.createUserMember("abc@abc.com", "1234", "ABC",
                new Address("서울", "종로", "000006"));
        Member userMember2 = Member.createUserMember("hij@hij.com", "1234", "HIJ",
                new Address("서울", "종로", "000006"));

        Member adminMember = Member.createAdminMember("efg@efg.com", "1234", "EFG",
                new Address("서울", "종로", "000006"));

        Member userResult1 = memberRepository.save(userMember1);
        Member userResult2 = memberRepository.save(userMember2);

        Member adminResult = memberRepository.save(adminMember);

        Group group = Group.createGroup("루키", "루키입니다", adminResult.getEmail());
        Group groupResult = groupRepository.save(group);

        Department department = FRONT_END;
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        QuestionAnswer[] EMPTY = new QuestionAnswer[0];

        GroupRequest groupRequest1 = GroupRequest.createGroupRequest(userResult1, groupResult,
                department, questionAnswerList.toArray(EMPTY));
        GroupRequest groupRequest2 = GroupRequest.createGroupRequest(userResult2, groupResult,
                department, questionAnswerList.toArray(EMPTY));

        groupRequestRepository.save(groupRequest1);
        groupRequestRepository.save(groupRequest2);

        /*
        when
         */

        GroupRequest findGroupRequest = groupRequestRepository.findByMemberEmailAndGroupName(userMember1.getEmail(),
                group.getName()).get();

        findGroupRequest.changeRequestStatus(RequestStatus.ACCEPT);

        GroupRequest groupRequest = groupRequestRepository.findByMemberEmailAndGroupName(userMember1.getEmail(),
                group.getName()).get();
        /*
        then
         */
        assertThat(groupRequest.getRequestStatus()).isEqualTo(RequestStatus.ACCEPT);
    }
}