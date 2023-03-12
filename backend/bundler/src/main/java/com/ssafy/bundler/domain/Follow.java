package com.ssafy.bundler.domain;

import java.io.Serializable;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "FOLLOWS", uniqueConstraints = @UniqueConstraint(columnNames = {"follow_to_id", "follow_from_id"}))
public class Follow extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_id")
	private Long followId;

	@Column(name = "follow_from_id")
	Long followFromId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follow_from_id", insertable = false, updatable = false)
	private User followFrom;

	// @OneToMany(fetch = FetchType.LAZY)
	// @JoinColumn(name = "follow_from_id", insertable = false, updatable = false)
	// @Builder.Default
	// private List<User> followingList = new ArrayList<>();

	@Column(name = "follow_to_id")
	Long followToId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follow_to_id", insertable = false, updatable = false)
	private User followTo;

	// @OneToMany(fetch = FetchType.LAZY)
	// @JoinColumn(name = "follow_to_id", insertable = false, updatable = false)
	// @Builder.Default
	// private List<User> followerList = new ArrayList<>();

	// @Builder
	// public Follow(User followTo, User followFrom) {
	// 	this.followTo = followTo;
	// 	this.followFrom = followFrom;
	// }

	// @Transient
	@ReadOnlyProperty
	private Integer followBackId; //맞팔인 follow_id

}
