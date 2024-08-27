package com.koview.koview_server.api.user.relation.withQuery.domain;

import com.koview.koview_server.api.common.BaseTimeEntity;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.user.query.domain.Query;

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
public class WithQuery extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "with_query_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "query_id")
	private Query query;

	public void linkMember(Member member) {
		if (this.member != null) {
			this.member.getWithQueryList().remove(this);
		}

		this.member = member;
		if (member != null) {
			member.getWithQueryList().add(this);
		}
	}

	public void linkQuery(Query query) {
		if (this.query != null) {
			this.query.getWithQueryList().remove(this);
		}

		this.query = query;
		if (query != null) {
			query.getWithQueryList().add(this);
		}
	}
}
