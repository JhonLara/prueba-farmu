package com.farmu.prueba.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "url")
public class URL {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Setter
	private long id;
	@Column(nullable = false)
	@Getter
	@Setter
	private String longUrl;

	public URL(long id, String longUrl) {
		super();
		this.id = id;
		this.longUrl = longUrl;
	}

	public URL() {
	}

}
