package com.example.controller;

import com.example.form.*;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.domain.ArticleDomain;
import com.example.domain.CommentDomain;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/sns")
public class SNSController {
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ArticleRepository articleRepository;
	
	@RequestMapping("")
	public String index(Model model) {
		List<ArticleDomain> articleList = articleRepository.findAll();
		
		model.addAttribute("articleList", articleList);
		return "top";
	}
	
	@RequestMapping("/post-article")
	public String postArticle(ArticleForm articleForm, Model model) {
		ArticleDomain articleDomain = new ArticleDomain();
		articleDomain.setName(articleForm.getName());
		articleDomain.setContent(articleForm.getContent());
		articleRepository.post(articleDomain);
		return "redirect:/sns";
	}
	@RequestMapping("/post-comment")
	public String postComment(CommentForm commentForm, Model model) {
		System.out.println("articleIDは" + commentForm.getArticleId());
		System.out.println("articleCommentは" + commentForm.getContent());
		CommentDomain comment = new CommentDomain();
		BeanUtils.copyProperties(commentForm, comment);
		commentRepository.postComment(comment);
		
		return "redirect:/sns";
	}
	
	@RequestMapping("/delete-article")
	public String deleteArticle(int articleId) {
		articleRepository.delete(articleId);
		return "redirect:/sns";
	}
}
