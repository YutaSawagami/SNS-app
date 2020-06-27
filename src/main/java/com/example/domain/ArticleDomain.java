package com.example.domain;

import java.util.List;

public class ArticleDomain {

	private Integer id;
	private String name;
	private String content;
	private List<CommentDomain> commentList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<CommentDomain> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<CommentDomain> commentList) {
		this.commentList = commentList;
	}
	
	
}
