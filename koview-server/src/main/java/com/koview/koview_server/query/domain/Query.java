package com.koview.koview_server.query.domain;

import java.util.ArrayList;
import java.util.List;

import com.koview.koview_server.global.common.BaseTimeEntity;
import com.koview.koview_server.image.domain.ReviewImage;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.withQuery.domain.WithQuery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Query extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "query_id")
	private Long id;

	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private Long totalWithQueriesCount = 0L;

	@OneToMany(mappedBy = "query", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WithQuery> withQueryList = new ArrayList<>();

	@OneToMany(mappedBy = "query", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QueryImage> queryImageList = new ArrayList<>();

	public void increaseTotalWithQueriesCount() {
		if (this.totalWithQueriesCount == null) {
			this.totalWithQueriesCount = 0L;
		}
		this.totalWithQueriesCount++;
	}

	public void decreaseTotalWithQueriesCount() {
		if (this.totalWithQueriesCount == null) {
			this.totalWithQueriesCount = 0L;
		}
		this.totalWithQueriesCount--;
	}

	public void addQueryImages(List<QueryImage> queryImages) {
		if(this.queryImageList ==  null) {
			this.queryImageList = new ArrayList<>();
		}
		for (QueryImage image : queryImages) {
			if (!this.queryImageList.contains(image)) {
				image.addQuery(this);
				this.queryImageList.add(image);
			}
		}
	}
}
