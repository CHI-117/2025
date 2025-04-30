package com.example.firstproject.controller;


import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    @Autowired
    private MemberRepository memberRepository;
    @GetMapping("/member/new")
    public String newMemberForm(){
        return "/member/new";
    }
    @PostMapping("/join")
    public String createMember(MemberForm form){
        log.info(form.toString());

        //System.out.println(form.toString());
        // 1. DTO를 엔티티로 변환
        Member member = form.toEntity();
        log.info(member.toString());
        //System.out.println(member.toString());
        // 2. 리파지터리로 엔티티를 DB에 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "redirect:/member/"+saved.getId();
    }
    @GetMapping("/member/{id}")
    public  String show(@PathVariable Long id, Model model){
        //1. 아이디 조회
        Member member=memberRepository.findById(id).orElse(null);
        //2. 모델 데이터등록
        model.addAttribute("member",member);
        //3. 뷰페이지 반환
        return "member/show";
    }
    @GetMapping("/member")
    public String index(Model model){
        //1.  모든 데이터 가져오기
        Iterable<Member> members=memberRepository.findAll();
        //2. 모델에 데이터 등록
        model.addAttribute("member",members);
        //3. 뷰페이지 설정
        return "member/index";
    }
    @GetMapping("/member/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        // 모델에 데이터 등록하기
        model.addAttribute("member",memberEntity);
        return "member/edit";
    }
    @PostMapping("/member/update")
    public String update(MemberForm form){
        log.info(form.toString());
        //1.DTO를 엔티티로 변환
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());
        //2.엔티티를 DB에 저장
        //2-1 DB에서 기존 데이터 가져오기
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        //2-2 기존 데이터값 갱신
        if(target!= null){
            memberRepository.save(memberEntity);
        }
        //3. 수정 결과 페이지로 리다이렉트
        return "redirect:/member/"+memberEntity.getId();

    }


}
