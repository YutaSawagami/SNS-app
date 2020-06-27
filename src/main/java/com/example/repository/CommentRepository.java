package com.example.repository;

import com.example.domain.CommentDomain;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate templete;
	
	private static final RowMapper<CommentDomain> COMMENT_ROW_MAPPER
	= (rs, i) -> {
		System.out.println("----------------");
		CommentDomain comment = new CommentDomain();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};
	
	public  List<CommentDomain> findByArticleId(int articleId){
		System.out.println("投稿IDは" + articleId);
		String sql = "select * from comments where article_id = :articleId order by id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<CommentDomain> commentList = templete.query(sql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}
	
	public void postComment(CommentDomain comment) {
		String sql = "insert into comments (name,content,article_id) values(:name,:content,:articleId)";
		String name = comment.getName();
		String content = comment.getContent();
		Integer articleId = comment.getArticleId();
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name)
				.addValue("content", content).addValue("articleId", articleId);
		templete.update(sql, param);
	}
	public void deleteComment(int articleId) {
		
		String sql = "delete from comments where article_id = :articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		templete.update(sql, param);
	}
}
