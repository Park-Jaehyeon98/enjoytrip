package com.ssafy.enjoytrip.model.dto;

public class BoardCommentDto {
	private int bcno;
	private String subject;
	private int bno;
	private String id;
	private String content;
	private String registerTime;

	public int getBcno() {
		return bcno;
	}

	public void setBcno(int bcno) {
		this.bcno = bcno;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
		return "BoardCommentDto [bcno=" + bcno + ", bno=" + bno + ", id=" + id + ", content=" + content
				+ ", registerTime=" + registerTime + "]";
	}

}
