package com.rest.example.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String symbol;
	private Date date;
	private Double previousClose;
	private Double price;

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPreviousClose(Double previousClose) {
		this.previousClose = previousClose;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

	public Date getDate() {
		return date;
	}

	public Double getPreviousClose() {
		return previousClose;
	}

	public Double getPrice() {
		return price;
	}
}
