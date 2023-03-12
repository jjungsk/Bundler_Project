package com.ssafy.bundler.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bundler.domain.Card;
import com.ssafy.bundler.domain.CardBundle;
import com.ssafy.bundler.domain.CardType;
import com.ssafy.bundler.domain.Category;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.dto.bundle.request.BundleScrapRequestDto;
import com.ssafy.bundler.dto.card.reqeust.CardListSaveRequestDto;
import com.ssafy.bundler.dto.card.reqeust.CardSaveRequestDto;
import com.ssafy.bundler.dto.card.reqeust.CardUpdateRequestDto;
import com.ssafy.bundler.exception.BusinessException;
import com.ssafy.bundler.exception.ErrorCode;
import com.ssafy.bundler.repository.CardBundleRepository;
import com.ssafy.bundler.repository.CardRepository;
import com.ssafy.bundler.repository.CategoryRepository;
import com.ssafy.bundler.repository.FeedCategoryRepository;
import com.ssafy.bundler.repository.FeedRepository;
import com.ssafy.bundler.repository.LinkRepository;
import com.ssafy.bundler.repository.UserRepository;
import com.ssafy.bundler.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *packageName    : com.ssafy.bundler.service
 * fileName       : CardService
 * author         : modsiw
 * date           : 2023/02/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/06        modsiw       삭제 리팩토링
 * 2023/02/10		 modsiw		  Jsoup 라이브러리 추가
 * 2023/02/15		 hyojin		  fileUploadToGithub() 추가
 * 2023/02/16 		 hyojin		  Card save하는 로직에 fileUploadToGithub() 추가
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CardService {
	private final FeedCategoryRepository feedCategoryRepository;

	// public static final String MD_FILE_SAVE_DIRECTORY = "c:" + File.separator + "bundler" + File.separator;
	public static final String REPOSITORY_NAME = "Bundler";
	public static final String REPOSITORY_DESCRIPTION = "Bundler 카드 모음집";
	public static final String BRANCH_NAME_MAIN = "main";
	// @Value("${spring.security.oauth2.client.registration.github.client-id}")
	private final String CLIENT_ID = "d747a562f5f038b5b11f";

	private final CardRepository cardRepository;
	private final FeedRepository feedRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final CardBundleRepository cardBundleRepository;
	private final LinkRepository linkRepository;

	//문제, 일반 카드 생성
	@Transactional
	public Long saveCard(CardSaveRequestDto requestDto) {

		log.info("requestDto.userId : " + requestDto.getUserId());

		User writerUser = userRepository.findByUserId(requestDto.getUserId()).orElseThrow(() ->
			new IllegalArgumentException("해당 유저가 존재하지 않습니다. userId= " + requestDto.getUserId()));

		Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() ->
			new IllegalArgumentException("해당 카테고리 아이디가 존재하지 않습니다. categoryId= " + requestDto.getCategory()));

		Card saveCard = cardRepository.save(requestDto.toEntity(writerUser, category));
		Long savedFeedId = saveCard.getFeedId();

		// String lineBreak = requestDto.getFeedContent().replace("\r\n", "<br>");
		// requestDto.setFeedContent(lineBreak);

		// if (requestDto.getCardType() == "CARD_LINK") {
		// 	saveLinkCard(savedFeedId);
		// }

		//Github push 로직 추가
		if (writerUser.getProviderType() != null) {
			try {
				fileUploadToGithub(writerUser, saveCard);
			} catch (IOException e) {
				throw new BusinessException("Github에서 오류남", ErrorCode.INTERNAL_SERVER_ERROR);
			}
		}

		return savedFeedId;
	}

	//링크 카드 생성
	@Transactional
	public void saveLinkCard(Long cardId) {
		Card card = cardRepository.findById(cardId).get();

		String targetLink = card.getCardDescription(); //링크는 cardDescription에 링크 url이 들어온다.
		Document document;
		try {
			//Get Document object after parsing the html from given url.
			document = Jsoup.connect(
					targetLink)
				.get();
			//Get keywords from document object.
			String description =
				document.select("meta[name=description]").get(0)
					.attr("content");
			//Print description.
			System.out.println("Meta Description: " + description);
			String title =
				document.select("meta[property=\"og:title\"]").get(0)
					.attr("content");
			//Print description.
			System.out.println("Meta title: " + title);

			System.out.println("Meta Description: " + description);
			String image =
				document.select("meta[property=\"og:image\"]").get(0)
					.attr("content");
			//Print description.
			System.out.println("Meta image: " + image);

			// Link.builder()
			// 	.cardId(cardId)
			// 	.linkUrl(card.getCardDescription())
			// 	.linkDescription(description)
			// 	.linkTitle(title)
			// 	.linkImage(image)
			// 	.build();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 여기서 링크 백단으로 받아서 저장 -> Jsoup 으로 하는거. 아니면 아예 메소드로만 빼도 될듯??

	//카드 리스트 받아왔을 때 번들로 안만들때
	@Transactional
	public void saveCardList(CardListSaveRequestDto requestDto) {
		int size = requestDto.getCardSaveRequestDtoList().size();
		for (int i = 0; i < size; i++) {
			CardSaveRequestDto cardSaveRequestDto = requestDto.getCardSaveRequestDtoList().get(i);
			String cardType = cardSaveRequestDto.getCardType();
			if (CardType.CARD_PROBLEM.toString().equals(cardType)) {
				saveCard(cardSaveRequestDto);
			} else if (CardType.CARD_GENERAL.toString().equals(cardType)) {
				saveCard(cardSaveRequestDto);
			} else {
				saveCard(cardSaveRequestDto);
			}
		}
	}

	@Transactional
	public List<Long> saveCardListwithBundle(List<CardSaveRequestDto> requestDto) {

		List<Long> savedCardList = new ArrayList<Long>();

		int size = requestDto.size();

		for (int i = 0; i < size; i++) {
			CardSaveRequestDto cardSaveRequestDto = requestDto.get(i);
			String cardType = cardSaveRequestDto.getCardType();
			if (CardType.CARD_PROBLEM.toString().equals(cardType)) {
				savedCardList.add(saveCard(cardSaveRequestDto));
			} else if (CardType.CARD_GENERAL.toString().equals(cardType)) {
				savedCardList.add(saveCard(cardSaveRequestDto));
			} else {
				//링크카드로 보내야하지만 아직 구현이 안됐음
				savedCardList.add(saveCard(cardSaveRequestDto));
			}
		}
		return savedCardList;
	}

	//수정 -> 링크카드와 일반카드일때 다름
	@Transactional
	public Long updateCard(Long feedId, CardUpdateRequestDto requestDto) {
		Card findCard = cardRepository.findById(feedId).orElseThrow(() ->
			new IllegalArgumentException("해당 카드를 찾을 수 없습니다. cardId(feedId)= " + feedId));

		Card newCard = cardRepository.save(findCard.toBuilder().feedId(feedId)
			.feedTitle(requestDto.getFeedTitle())
			.feedContent(requestDto.getFeedContent())
			.cardDescription(requestDto.getCardDescription())
			.cardCommentary(requestDto.getCardCommentary())
			.category(categoryRepository.findById(requestDto.getCategoryId()).get())
			.build());

		//Github push 로직 추가
		try {
			fileUploadToGithub(newCard.getWriter(), newCard);
		} catch (IOException e) {
			throw new BusinessException("Github에서 오류남", ErrorCode.INTERNAL_SERVER_ERROR);
		}

		return feedId;
	}

	//삭제 ver1 (isDeleted=true)
	@Transactional
	public Long deleteCardV1(Long feedId) {
		Card findCard = cardRepository.findById(feedId).orElseThrow(() ->
			new IllegalArgumentException("해당 카드를 찾을 수 없습니다. cardId(feedId)= " + feedId));

		findCard.deleteFeed();

		return feedId;
	}

	//카드 삭제 ver2 (entity delete)
	@Transactional
	public Long deleteCardV2(Long feedId) {

		List<CardBundle> cardBundles = cardBundleRepository.findAllByCardId(feedId);

		for (CardBundle cardBundle : cardBundles) {
			Long cardBundleId = cardBundle.getCardBundleId();
			cardBundleRepository.deleteById(cardBundleId);
		}

		Card card = cardRepository.findById(feedId).orElseThrow(() -> new IllegalArgumentException(
			"해당 카드를 찾을 수 없습니다. cardId= " + feedId));

		cardRepository.delete(card);

		return feedId;
	}

	//===== Bundle =====//
	@Transactional
	public void scrapCardWithExistBundle(BundleScrapRequestDto requestDto) {
		//카드 번들이 아이디가 똑같을때
		if (requestDto.getCardId() == requestDto.getBundleId()) {
			throw new IllegalArgumentException("카드와 번들의 Id는 같을 수 없습니다.");
		}

		//카드의 scrapCnt + 1
		Card card = cardRepository.findById(requestDto.getCardId()).orElseThrow(() ->
			new IllegalArgumentException("해당 카드의 id를 찾을 수 없습니다. cardId(feedId)= " + requestDto.getCardId()));
		card.addCardScrapCnt();

		//CardBundle에 넣어주기 -> 이미 번들에 존재하는 카드면 넣으면 안됨
		validateIsDuplicatedCardInBundle(requestDto.getBundleId(), requestDto.getCardId());
		saveCardBundle(requestDto.getBundleId(), requestDto.getCardId());
	}

	//CardBundle 객체 생성
	@Transactional
	public void saveCardBundle(Long bundleId, Long cardId) {
		cardBundleRepository.save(CardBundle.builder()
			.bundleId(bundleId)
			.cardId(cardId)
			.build());
	}

	//검증로직
	private void validateIsDuplicatedCardInBundle(Long bundleId, Long cardId) {
		if (cardBundleRepository.findCardBundleByBundleIdWithCardId(bundleId, cardId) != null) {
			throw new IllegalArgumentException("이미 해당 번들에 존재하는 카드입니다.");
		}
	}

	public void fileUploadToGithub(User user, Card card) throws IOException {
		String accessToken = user.getProviderAccessToken();
		String loginName = user.getGithubLoginName();

		log.info("accessToken : " + accessToken);
		log.info("loginName : " + loginName);

		String commitMessage = String.format("[%s-%s] %s",
			card.getCategory().getParent().getCategoryName(),
			card.getCategory().getCategoryName(),
			card.getFeedTitle());

		log.info("commitMessage ------------------------------");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		LocalDate cardCreatedDay = LocalDate.from(card.getCreatedAt());

		log.info("cardCreatedDay ------------------------------");

		String commitPath = formatter.format(cardCreatedDay) + "/" + card.getFeedTitle() + ".md";

		log.info("commitPath ------------------------------");

		///////////////////////////////////////////////////////////////

		/* 인증을 통해 github 정보 가져오기 */
		//	1. accessToken 갱신
		// try { // 404 error
		// 	GHAuthorization auth = new GitHubBuilder()
		// 		.withOAuthToken(accessToken, loginName)
		// 		.build()
		// 		.resetAuth(CLIENT_ID, accessToken);
		//
		// 	log.info(auth.getToken());
		//
		// 	user.setProviderAccessToken(auth.getToken());
		// 	userRepository.save(user);
		// } catch (Exception e) {
		// 	log.info(e.getMessage());
		// 	// throw new GithubHttpRequestException("Github 404 에러", ErrorCode.GITHUB_AUTHENTICATION_TOKEN_INVALID);
		// }

		// try { // 404 error
		// 	// POST 메소드 URL 생성 & header setting
		// 	HttpClient client = HttpClientBuilder.create().build();
		// 	HttpPatch patchRequest = new HttpPatch("https://api.github.com/applications/" + cliendId + "/token");
		// 	patchRequest.setHeader("Accept", "application/vnd.github+json");
		// 	patchRequest.setHeader("X-GitHub-Api-Version", "2022-11-28");
		//
		// 	// post body setting
		// 	ObjectMapper mapper = new ObjectMapper();
		// 	String jsonMessage = "{\"access_token\": \"" + user.getProviderAccessToken() + "\"}";
		// 	patchRequest.setEntity(new StringEntity(mapper.writeValueAsString(jsonMessage)));
		//
		// 	// CURL execute
		// 	HttpResponse response = client.execute(patchRequest);
		//
		// 	//Response 출력
		// 	if (response.getStatusLine().getStatusCode() == 200) {
		// 		ResponseHandler<String> handler = new BasicResponseHandler();
		// 		String body = handler.handleResponse(response);
		// 		log.info("response 받아옴");
		// 		log.info(body);
		// 		System.out.println(body);
		// 	} else {
		// 		log.info("response is error : " + response.getStatusLine().getReasonPhrase());
		// 		System.out.println("response is error : " + response.getStatusLine().getStatusCode());
		// 		log.info("response is error : " + response.toString());
		//
		// 	}
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// 	System.err.println(e.toString());
		// }

		// accessToken = auth.getToken();

		//	2. 갱신된 토큰으로 github 정보 가져오기
		GitHub github = null;
		try {
			github = new GitHubBuilder()
				.withOAuthToken(accessToken, loginName)
				.build();
		} catch (Exception e) {
			log.error("000000000000000000  " + e.getMessage());
		}

		/* repository 생성 */
		GHRepository repo = null;
		try {
			repo = github.getRepository(loginName + "/" + REPOSITORY_NAME);
		} catch (Exception e) { //기존 Bundler repository가 없으면 생성
			log.error("11111111111111111111" + e.getMessage());

			repo = github.createRepository(REPOSITORY_NAME)
				.autoInit(true)
				.owner(loginName) //github login name으로 repository의 owner 설정
				.private_(false)
				.description(REPOSITORY_DESCRIPTION)
				.defaultBranch(BRANCH_NAME_MAIN)
				.create();
		}

		/* 이전 커밋의 해시 얻어오기 */
		// GHRepository getRepo = github.getRepository("Bundler");
		// String sha1 = getRepo.getBranch("main").getSHA1();
		String sha1 = null;
		try {
			sha1 = repo.getBranch(BRANCH_NAME_MAIN).getSHA1();
		} catch (Exception e) {
			log.error("22222222222222" + e.getMessage());
		}

		/* 파일 생성 (굳이 파일을 I/O 할 필요 없어서 생략) */
		// String directory = "c:" + File.separator + "bundler" + File.separator;
		// File file = new File(MD_FILE_SAVE_DIRECTORY + "tmp_file13.md");
		// File file = File.createTempFile("temp", ".md", directory);

		// String str = "# Hello world!";
		// try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
		// 	writer.write(str);
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		//
		// byte[] fileContent = FileUtils.readFileToByteArray(file);

		/* repository에 파일 커밋 */
		try {
			GHContent content = repo.getFileContent(commitPath);

			content.update( //이미 존재하면 기존 파일 수정
				new FileUtil().write(card.getFeedTitle(), card.getFeedContent()),
				commitMessage + " 수정",
				BRANCH_NAME_MAIN
			);
		} catch (Exception e) { //존재하지 않으면 파일 새로 생성
			log.error("33333333333333" + e.getMessage());

			repo.createContent()
				.branch(BRANCH_NAME_MAIN)
				.content(new FileUtil().write(card.getFeedTitle(), card.getFeedContent()))
				.message(commitMessage)
				.path(commitPath)
				.sha(sha1)
				.commit();
		}

	}

}
