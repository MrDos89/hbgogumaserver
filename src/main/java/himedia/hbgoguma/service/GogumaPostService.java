package himedia.hbgoguma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himedia.hbgoguma.mappers.GogumaPostMapper;
import himedia.hbgoguma.repository.vo.GogumaPost;

@Service
public class GogumaPostService {
	@Autowired
	private GogumaPostMapper gogumaPostMapper;
	
	public List<GogumaPost> selectAllPosts() {
		List<GogumaPost> posts = gogumaPostMapper.selectAllPosts();
		
		return posts;
	}
	
	public GogumaPost selectPostByPid(Long pid) {
		return gogumaPostMapper.selectPostByPid(pid);
	}
	
	public List<GogumaPost> selectMyPosts(Long uid) {
		List<GogumaPost> posts = gogumaPostMapper.selectMyPosts(uid);
		
		return posts;
	}
	
	public List<GogumaPost> selectRelatedPosts(GogumaPost post) {
		List<GogumaPost> posts = gogumaPostMapper.selectRelatedPosts(post);
		
		return posts;
	}
	
	public GogumaPost insertPost(GogumaPost post) {
		gogumaPostMapper.insertPost(post);
		
		Long pid = post.getPid();
		
		return gogumaPostMapper.selectPostByPid(pid);
	}
	
	public GogumaPost updatePostUserList(GogumaPost post) {
		gogumaPostMapper.updatePostUserList(post);
		
		return post;
	}
	
	public GogumaPost updatePost(GogumaPost post) {
		gogumaPostMapper.updatePost(post);
		
		return post;
	}
	
	public int deletePost(Long pid) {
		return gogumaPostMapper.deletePost(pid);
	}
}
