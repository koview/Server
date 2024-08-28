package com.koview.koview_server.api.common.purchaseLink.domain;

import com.koview.koview_server.api.user.query.domain.Query;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryPurchaseLink {
    @Id
    @Column(name = "query_purchase_link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_link_id", nullable = true)
    private PurchaseLink purchaseLink;

    @ManyToOne
    @JoinColumn(name = "query_id", nullable = false)
    private Query query;

    public void linkQuery(Query query){
        if(query.getQueryPurchaseLinkList() == null)
            // 왜인지 모르나 Builder.Default를 해도 queryPurchaseLinkList가 null이 되네요.
            query.setQueryPurchaseLinkList(new ArrayList<>());

        if(this.query != null) {
            this.query.getQueryPurchaseLinkList().remove(this);

        }
        this.query=query;
        this.query.getQueryPurchaseLinkList().add(this);
    }

    public void unLink(){
        if(this.query != null){
            this.query.getQueryPurchaseLinkList().remove(this);
            this.query=null;
        }
    }
}
