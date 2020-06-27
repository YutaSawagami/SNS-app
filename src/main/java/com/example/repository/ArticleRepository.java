package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.example.domain.ArticleDomain;
import com.example.domain.CommentDomain;

@Repository
public class ArticleRepository {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate templete;
	
	private static final RowMapper<ArticleDomain> ARTICLE_ROW_MAPPER 
	= (rs, i) -> {
		ArticleDomain article = new ArticleDomain();
		int id = rs.getInt("id");
		article.setId(id);
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
	
	public List<ArticleDomain> findAll(){
		String sql = "select id,name,content from articles order by id desc";
		List<ArticleDomain> articleList = templete.query(sql, ARTICLE_ROW_MAPPER);
		for(ArticleDomain article: articleList) {
			article.setCommentList(commentRepository.findByArticleId(article.getId()));
		}
		return articleList;
	}
	
	public void post(ArticleDomain articleDomain) {
		String sql = "insert into articles (name,content) values(:name,:content)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", articleDomain.getName()).addValue("content", articleDomain.getContent());
		templete.update(sql, param);
	}
	
	public void delete(int id) {
		commentRepository.deleteComment(id);
		String sql = "delete from articles where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		templete.update(sql, param);
	}
}
