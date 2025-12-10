package com.ouss.msa.jmsactivemqtopicexample.jms;


import java.io.Serializable;

// Si tu utilises lombok, tu peux remplacer par annotations @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Article implements Serializable {
private Long id;
private String description;
private Double price;

public Article() {}

public Article(Long id, String description, Double price) {
	this.id = id;
	this.description = description;
	this.price = price;
}

public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }

public Double getPrice() { return price; }
public void setPrice(Double price) { this.price = price; }

@Override
public String toString() {
	return "Article(id=" + id + ", description=" + description + ", price=" + price + ")";
}
}