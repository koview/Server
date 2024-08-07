package com.koview.koview_server.query.domain;

import com.koview.koview_server.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryImage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "query_image_id")
	private Long id;

	// @Column(unique = true)
	private String url;

	@ManyToOne
	@JoinColumn(name = "query_id")
	private Query query;

	public void addQuery(Query query) {
		this.query = query;

		if (query.getQueryImageList() != null && !query.getQueryImageList().contains(this)) {
			query.getQueryImageList().add(this);
		}
	}
}
