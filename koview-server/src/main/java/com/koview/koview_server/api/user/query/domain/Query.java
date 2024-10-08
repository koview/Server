package com.koview.koview_server.api.user.query.domain;

import java.util.ArrayList;
import java.util.List;

import com.koview.koview_server.api.common.BaseTimeEntity;
import com.koview.koview_server.api.image.domain.QueryImage;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.common.purchaseLink.domain.QueryPurchaseLink;
import com.koview.koview_server.api.user.relation.withQuery.domain.WithQuery;

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

import static lombok.Builder.*;

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
	@Default
	private Long totalWithQueriesCount = 0L;
	@Default
	private Long totalViewCount = 0L;

	@Default
	@OneToMany(mappedBy = "query", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QueryPurchaseLink> queryPurchaseLinkList = new ArrayList<>();

	@Default
	@OneToMany(mappedBy = "query", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WithQuery> withQueryList = new ArrayList<>();

	@Default
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

	public void increaseTotalViewCount() {
		if (this.totalViewCount == null) {
			this.totalViewCount = 0L;
		}
		this.totalViewCount++;
	}

	public void linkQueryImages(List<QueryImage> queryImages) {
		if(this.queryImageList ==  null) {
			this.queryImageList = new ArrayList<>();
		}
		for (QueryImage image : queryImages) {
			if (!this.queryImageList.contains(image)) {
				image.linkQuery(this);
				this.queryImageList.add(image);
			}
		}
	}

	public void linkMember(Member member){
		if(this.member != null){
			this.member.getQueryList().remove(this);
		}
		this.member = member;
		this.member.getQueryList().add(this);
	}

	public void unLink(){
		if(this.member != null){
			this.member.getQueryList().remove(this);
			this.member = null;
		}
	}

}
