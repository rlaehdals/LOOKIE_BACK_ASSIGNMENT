package com.example.lookie.member.service;

import com.example.lookie.member.domain.Address;
import com.example.lookie.member.domain.Role;

public interface MemberService {

    Long signup(String email, String password, Role role, String name, Address address);
    void withdrawal(String email);
    void changePassword(String email,String password);
    void changeName(String email,String name);
}
