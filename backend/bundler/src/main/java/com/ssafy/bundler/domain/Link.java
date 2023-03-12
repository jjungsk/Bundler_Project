package com.ssafy.bundler.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "LINKS")
@DiscriminatorValue(value = "CARD_LINK")
@PrimaryKeyJoinColumn(name = "link_id")
public class Link extends Card implements Serializable {

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id", insertable = false, updatable = false)
	private Long linkId;

	@Column(name = "link_url", nullable = false)
	private String linkUrl;

	@Column(name = "link_image")
	private String linkImage;

	@Column(name = "link_title")
	private String linkTitle;

	@Column(name = "link_description")
	private String linkDescription;

}
