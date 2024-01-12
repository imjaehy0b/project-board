package com.project.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("view 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;


    public ArticleControllerTest(@Autowired MockMvc mvc) {//테스트 영역은 autowired 생략 불가.
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
        //given

        //when&then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk()) //상태 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) //타입검사
                .andExpect(view().name("articles/index")) //뷰 체크
                .andExpect(model().attributeExists("articles")); //데이터가 존재하는지 체크
    }

    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnArticleView() throws Exception {
        //given

        //when&then
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk()) //상태 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) //타입검사
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article")) //데이터 검사
                .andExpect(model().attributeExists("articleComments"));
    }

    @Disabled("구현중")
    @DisplayName("[view][GET] 게시글 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnArticleSearchView() throws Exception {
        //given

        //when&then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk()) //상태 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) //타입검사
                .andExpect(view().name("articles/search"));
    }

    @Disabled("구현중")
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnArticleHashtagSearchView() throws Exception {
        //given

        //when&then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk()) //상태 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) //타입검사
                .andExpect(view().name("articles/search-hashtag"));
    }
}