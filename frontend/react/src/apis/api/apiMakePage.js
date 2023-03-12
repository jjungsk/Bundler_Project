// [Import] api instance
import apiInstance from "apis/utils/axios";

// api Instance 생성
const api = apiInstance();

// controller default page url
const CARD_CONTROLLER = "/api/v1/cards";

// 카드 - 리스트 생성
const apiPostCardList = async (params) => {
  try {
    const response = await api.post(`${CARD_CONTROLLER}/list`, params);
    return response;
  } catch (error) {
    return error;
  }
};

// 카드 - 개별 생성
const apiPostCard = async (params) => {
  try {
    const response = await api.post(`${CARD_CONTROLLER}`, params);
    return response;
  } catch (error) {
    return error;
  }
};

// 번들 생성
const apiPostBundle = async (params) => {
  try {
    const response = await api.post(`/api/v1/bundles`, params);
    return response;
  } catch (error) {
    return error;
  }
};

// export 함수
export { apiPostCardList, apiPostCard, apiPostBundle };
