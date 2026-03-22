package com.example.demo.entity;


import jakarta.persistence.Id;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "faq-questions")
public class FaqQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "StatusId")
    private Long statusId;

    @Column(name = "Questions")
    private String questions;

    @Column(name = "Answers")
    private String answers;

	public FaqQuestions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FaqQuestions(Long id, Long statusId, String questions, String answers) {
		super();
		this.id = id;
		this.statusId = statusId;
		this.questions = questions;
		this.answers = answers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	
    // getters & setters
    
}

