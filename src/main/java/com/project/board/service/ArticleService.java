package com.project.board.service;

import com.project.board.domain.Article;
import com.project.board.domain.type.SearchType;
import com.project.board.dto.ArticleDto;
import com.project.board.dto.ArticleWithCommentsDto;
import com.project.board.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()){
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType){
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword,pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword,pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword,pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword,pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#"+searchKeyword,pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true) // 단건 조회
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(()->new EntityNotFoundException("게시글이 없습니다 - articleId: "+articleId));
    }

    public void saveArticle(ArticleDto articleDto) {
        articleRepository.save(articleDto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
//        Optional<Article> article = articleRepository.findById(dto.id());
//        기존의 findById는 select 쿼리문을 발생시키므로 옵셔널을 사용해야한다.
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            if (dto.title() != null) article.setTitle(dto.title());
            if (dto.content() !=null) article.setContent(dto.content());
            article.setHashtag(dto.hashtag());
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}",dto);
        }

    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }
}
