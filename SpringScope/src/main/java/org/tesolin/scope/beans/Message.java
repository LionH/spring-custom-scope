package org.tesolin.scope.beans;

import java.io.Serializable;

public class Message implements Serializable, Comparable<Message>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6482885212684670291L;
	
	private Integer id;
	private String sender;
	private String conversation;
	private String content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getConversation() {
		return conversation;
	}
	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Message [sender=" + sender + ", conversation=" + conversation
				+ ", content=" + content + "]";
	}
	@Override
	public int compareTo(Message o) {
		if (o!=null) {
			return this.getId().compareTo(o.getId());
		}
		return 0;
	}
}
