package com.example.loginapi.controller;

import com.example.loginapi.domain.Member;
import com.example.loginapi.dto.Login.MemberLoginDto;
import com.example.loginapi.dto.Signup.MemberSignupDto;
import com.example.loginapi.dto.Signup.MemberSignupResponseDto;
import com.example.loginapi.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")

public class MemberController {
    private final MemberService memeberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Member member = new Member();
        member.setName(memberSignupDto.getName());
        member.setEmail(memberSignupDto.getName());
        member.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));

        Member saveMember = MemberService.addMember(member);
        // Spring Data Jpa 통해 MemberRepository.save 할 수도 있지만,
        // 추가로 role 권한을 설정해줘야 하기 때문에 Service에서 수행
        // -> 물론 MemberRepository.save 하기만 해도 Service로 분리하는 것이 좋음.

        MemberSignupResponseDto memberSignupResponseDto = new MemberSignupResponseDto();
        memberSignupResponseDto.setMemberId(saveMember.getMemberId());
        memberSignupResponseDto.setEmail(saveMember.getEmail());
        memberSignupResponseDto.setPassword(saveMember.getPassword());
        // Member 객체가 만들어지면서 필드의 regdate 가 자동 생성됨.
        memberSignupResponseDto.setRegdate(saveMember.getRegdate());

        // 회원가입
        return new ResponseEntity(memberSignupResponseDto, HttpStatus.OK);


    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDto memberLoginDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Member member = new Member();



    }


}
