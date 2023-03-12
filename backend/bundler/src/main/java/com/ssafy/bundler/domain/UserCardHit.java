package com.ssafy.bundler.domain;

import static jakarta.persistence.FetchType.*;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER_CARD_HIT", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "card_id"}))
public class UserCardHit implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_card_id")
	private Long userCardId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "card_id", referencedColumnName = "card_id")
	private Card card;

	@Column(name = "user_submit", length = 3000)
	private String userSubmit;

	@Builder
	public UserCardHit(User user, Card card, String userSubmit) {
		this.user = user;
		this.card = card;
		this.userSubmit = userSubmit;
	}

}
