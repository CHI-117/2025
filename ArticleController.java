package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Slf4j
@Controller
public class ArticleController {
    @Autowired //스프링 부트가 미리 생성해놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return  "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString());
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());
        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id= "+id);
        // 1. id 조회해서 데이터 겟
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        // 3. 뷰 페이지 반환(보여주기)
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        //1.  모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        //2. 모델에 데이터 등록
        model.addAttribute("articleList",articleEntityList);
        //3. 뷰페이지 설정
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //1.DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2.엔티티를 DB에 저장
        //2-1 DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2 기존 데이터값 갱신
        if(target!= null){
            articleRepository.save(articleEntity);
        }
        //3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/"+articleEntity.getId();

    }


}

