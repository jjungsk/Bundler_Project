package com.ssafy.bundler.controller;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.bundler.repository.FeedRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeedControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private FeedRepository feedRepository;
	@After
	public void delete(){
		System.out.println("delete genertatedFeedId :: "+genertatedFeedId);
		feedRepository.deleteById(genertatedFeedId);
	}
	private Long genertatedFeedId;
	@Test
	void likeFeed() {
		//given
		// Feed feed = Feed.builder().feedTitle("title").feedContent("content").build();
		// genertatedFeedId = feedRepository.save(feed).getFeedId();
		// System.out.println("genertatedFeedId :: "+genertatedFeedId);
		//
		// String url = "http://localhost:"+port+"/api/v1/feeds/like/"+genertatedFeedId;
		// FeedLikeRequestDto requestDto = new FeedLikeRequestDto();
		// requestDto.setUserId(1L);
		//
		// //when
		//
		// ResponseEntity<Object> responseEntity =
		// 	testRestTemplate.postForEntity(url,requestDto, Object.class);
		//
		// //then
		// assertThat(responseEntity.getBody().toString()).isEqualTo("{like=true}");
		// Feed nfeed = feedRepository.findById(genertatedFeedId).orElseThrow();
		// assertThat(nfeed.getFeedLikeCnt()).isEqualTo(1);
		// //when
		//
		// responseEntity =
		// 	testRestTemplate.postForEntity(url,requestDto, Object.class);
		//
		// nfeed = feedRepository.findById(genertatedFeedId).orElseThrow();
		// //then
		// assertThat(responseEntity.getBody().toString()).isEqualTo("{like=false}");
	 	// assertThat(nfeed.getFeedLikeCnt()).isEqualTo(0);
	}
}