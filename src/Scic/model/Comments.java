package model;


import java.sql.Timestamp;

public class Comments {

	int commentId;
	Offenses offense;
	Users user;
	String comment;
	Timestamp time;
	
	public Comments(int commentId, Offenses offense, Users user, String comment, Timestamp time) {
		this.comment=comment;
		this.commentId = commentId;
		this.offense = offense;
		this.user = user;
		this.time = time;
	}
	
	public Comments(int commentId) {
		this.commentId = commentId;
	}
	
	public Comments(Offenses offense, Users user, String comment, Timestamp time) {
		this.comment=comment;
		this.offense = offense;
		this.user = user;
		this.time = time;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Offenses getOffense() {
		return offense;
	}

	public void setOffense(Offenses offense) {
		this.offense = offense;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
}
