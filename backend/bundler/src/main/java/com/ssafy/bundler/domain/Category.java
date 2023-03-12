package com.ssafy.bundler.domain;

import static jakarta.persistence.FetchType.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "CATEGORIES")
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "category_name")
	private String categoryName;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Card> cardList = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "category_parent_id", referencedColumnName = "category_id")
	private Category parent;

	@Builder.Default
	@OneToMany(mappedBy = "parent") //셀프의 연관관계를 건거라고 생각하면 된다.
	private List<Category> child = new ArrayList<>();

	//==연관관계 편의 메서드==// parent니까 셀프
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
}
