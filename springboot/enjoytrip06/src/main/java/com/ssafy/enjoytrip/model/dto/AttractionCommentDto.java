package com.ssafy.enjoytrip.model.dto;

public class AttractionCommentDto {
	private int acno;
	private int contentId;
	private String title;
	private String id;
	private String content;
	private String registerTime;

	public int getAcno() {
		return acno;
	}

	public void setAcno(int acno) {
		this.acno = acno;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	@Override
	public String toString() {
		return "AttractionCommentDto [acno=" + acno + ", contentId=" + contentId + ", id=" + id + ", content=" + content
				+ ", registerTime=" + registerTime + "]";
	}

}
