package com.ssafy.bundler.dto.card.reqeust;

import java.util.List;

import lombok.Data;

@Data
public class CardListSaveRequestDto {
	List<CardSaveRequestDto> cardSaveRequestDtoList;
}
