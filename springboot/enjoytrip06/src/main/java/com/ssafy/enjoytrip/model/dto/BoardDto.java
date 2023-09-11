package com.ssafy.enjoytrip.model.dto;

public class BoardDto {
	private int bno;
	private String subject;
	private String id;
	private String content;
	private int hit;
	private String registerTime;

	public int getbno() {
		return bno;
	}

	public void setbno(int bno) {
		this.bno = bno;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	@Override
	public String toString() {
		return "BoardDto [bno=" + bno + ", subject=" + subject + ", id=" + id + ", content=" + content
				+ ", hit=" + hit + ", registerTime=" + registerTime + "]";
	}

}
