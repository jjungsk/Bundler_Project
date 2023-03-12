// 1. Reducer의 초기 state 지정
const initialState = {
  loginInfo: {
    userId: 5,
    userNickname: "섹쉬토끼",
    userEmail: "sexyRabbit@gmail.com",
  },
  feedList: [],
  bundleCardId: -1,
};

// 2. Type에 따른 state 변화 설정 && state가 변화할 때 수행되는 함수
const mainHomeReducer = (state = initialState, action) => {
  switch (action.type) {
    case "GET_FEED_LIST": {
      return {
        // spread 연산자를 이용하여 기존 객체를 불러옴
        ...state,
        cardList: [...state.cardList, { ...action.payload }],
        cardNo: state.cardNo + 1,
      };
    }
    case "BUNDLE_CARD_ID": {
      return {
        // spread 연산자를 이용하여 기존 객체를 불러옴
        ...state,
        bundleCardId: action.payload,
      };
    }
    default:
      return state;
  }
};

export default mainHomeReducer;

// testData: [...state.testData, { ...action.payload }],
// testDataL state.testDate.concat(action.payload),

// default: {
//   return { state };
// }

/*
  ...state의 ... 의 의미는 변화된 state만을 받는다는 의미이다. 즉, state에 변화가 없다면 기존값 그대로 반환
  마지막 default: return state;는 필수
*/
