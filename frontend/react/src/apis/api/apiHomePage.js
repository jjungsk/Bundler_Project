// [Import] api instance
import apiInstance from "apis/utils/axios";

// Import - service
import { sortFeedList, listId } from "apis/service/serviceHomePage";

// api Instance 생성
const api = apiInstance();

// controller default page url
const FEED_CONTROLLER = "/api/v1";

// ================================= Feed ================================================
// Feed List 가져오기
// 팔로우 팔로워 유저들만 : /api/v5/users/main/${ FEED_CONTROLLER }/feeds
// 전체 조회 api : /api/v1/feeds
const apiGetFeeds = async () => {
  try {
    const response = await api.get(`/api/v1/feeds`);
    const sorted = sortFeedList(response.data);
    const feedIdList = listId(sorted);
    return { sorted, feedIdList };
  } catch (error) {
    console.log(error);
    return error;
  }
};

// Card 상세 정보 가져오기
const apiGetCardDetail = async (cardId) => {
  try {
    const response = await api.get(`${FEED_CONTROLLER}/feeds/cards/${cardId}`);
    return response;
  } catch (error) {
    return error;
  }
};

// 번들 상세 정보 가져오기
const apiGetBundleDetail = async (bundleId) => {
  try {
    const response = await api.get(`/api/v5/feeds/bundles/${bundleId}`);
    return response;
  } catch (error) {
    return error;
  }
};

// ================================= Scrap ================================================[
// 스크랩 버튼 클릭 시 -> 내가 가지고 있는 번들 lsit 목록 불러오기
const apiGetBundle = async (userId, cardId) => {
  try {
    const response = await api.get(`api/v1/users/${userId}/bundles/summary?card_id=${cardId}`);
    return response;
  } catch (error) {
    return error;
  }
};

// 스크랩 카드 : 기존 "번들"에 "카드" 추가
const apiPutCardScrap = async (params) => {
  try {
    const response = await api.put(`${FEED_CONTROLLER}/scrap/cards`, params);
    return response;
  } catch (error) {
    return error;
  }
};

// 스크랩 카드 : "번들 생성" 하며 "카드" 추가
const apiPostCardScrap = async (feedId, params) => {
  try {
    const response = await api.post(`${FEED_CONTROLLER}/scrap/cards/bundles/${feedId}`, params);
    return response;
  } catch (error) {
    return error;
  }
};

// 스크랩 카드 : 이미 번들에 추가된 카드 삭제
const apiDeleteCardScrap = async (bundleId, cardId) => {
  try {
    const response = await api.delete(
      `${FEED_CONTROLLER}/scrap/bundles/${bundleId}/cards/${cardId}`
    );
    return response;
  } catch (error) {
    return error;
  }
};

// 스크랩 번들 : 번들 list 추가
const apiPostBundleScrap = async (params) => {
  try {
    const response = await api.post(`${FEED_CONTROLLER}/scrap/bundles`, params);
    return response;
  } catch (error) {
    return error;
  }
};

// ================================= Comment ===============================================
// 댓글 Create
const apiPostComment = async (params) => {
  try {
    const response = await api.post(`${FEED_CONTROLLER}/comment`, params);
    return response;
  } catch (error) {
    return error;
  }
};

// 댓글 Update
const apiPutComment = async (commentId, comment) => {
  try {
    const response = await api.put(`${FEED_CONTROLLER}/comment/${commentId}`, { content: comment });
    return response;
  } catch (error) {
    return error;
  }
};

// 댓글 Delete
const apiDeleteComment = async (commentId) => {
  try {
    const response = await api.delete(`${FEED_CONTROLLER}/comment/${commentId}`);
    return response;
  } catch (error) {
    return error;
  }
};

// ================================= FeedLike ============================
// 좋아요 상태 확인
const apiGetLike = async (feedId, userId) => {
  try {
    const response = await api.get(`${FEED_CONTROLLER}/feeds/like/${feedId}?user_id=${userId}`);
    return response;
  } catch (error) {
    return error;
  }
};

// 좋아요 누르기 or 취소
const apiPostLike = async (feedId, userId) => {
  try {
    const response = await api.post(`${FEED_CONTROLLER}/feeds/like/${feedId}`, userId);
    return response;
  } catch (error) {
    return error;
  }
};

// export 함수
export {
  apiGetFeeds,
  apiGetCardDetail,
  apiGetBundleDetail,
  apiGetBundle,
  apiPutCardScrap,
  apiPostCardScrap,
  apiDeleteCardScrap,
  apiPostBundleScrap,
  apiPostComment,
  apiPutComment,
  apiDeleteComment,
  apiGetLike,
  apiPostLike,
};
