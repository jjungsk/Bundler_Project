package com.ssafy.bundler.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BUNDLES")
@DiscriminatorValue(value = FeedType.Values.BUNDLE)
@PrimaryKeyJoinColumn(name = "bundle_id")
@SuperBuilder(toBuilder = true)
public class Bundle extends Feed implements Serializable {

	@Column(name = "bundle_id", insertable = false, updatable = false)
	private Long bundleId;

	@Column(name = "bundle_thumbnail", length = 3000)
	private String bundleThumbnail;

	@Column(name = "bundle_thumbnail_text", length = 3000)
	private String bundleThumbnailText;

	@Column(name = "is_bundle_private")
	private boolean isBundlePrivate;

	@Column(name = "is_bundle_default")
	private boolean isBundleDefault;

	@Column(name = "bundle_thumbnail_file_name", length = 3000)
	private String bundleThumbnailFileName;

	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "bundle_id")
	private List<CardBundle> cardList = new ArrayList<>();

	public void addCardBundle(final CardBundle cardBundle) {
		cardList.add(cardBundle);
	}

	// @Transient
	// @Column(name = "feed_type", insertable = false, updatable = false)
	// protected String feedType;

	@Transient
	@Column(name = "feed_type")
	@Enumerated(EnumType.STRING)
	private FeedType feedType;
}
