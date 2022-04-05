package com.cryptolisting.springreactjs.models;

public class OneSecMailResponse {
    private Long id;
    private String from;
    private String subject;
    private String date;
    private Object[] attachments;
    private String body;
    private String textBody;
    private String htmlBody;

    public OneSecMailResponse() {
    }

    public OneSecMailResponse(Long id, String from, String subject, String date) {
        this.id = id;
        this.from = from;
        this.subject = subject;
        this.date = date;
    }

    public OneSecMailResponse(Long id, String from, String subject, String date, Object[] attachments, String body, String textBody, String htmlBody) {
        this.id = id;
        this.from = from;
        this.subject = subject;
        this.date = date;
        this.attachments = attachments;
        this.body = body;
        this.textBody = textBody;
        this.htmlBody = htmlBody;
    }

    public Object[] getAttachments() {
        return attachments;
    }

    public void setAttachments(Object[] attachments) {
        this.attachments = attachments;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
