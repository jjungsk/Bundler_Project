// 1. Reducer의 초기 state 지정
const initialState = {
  isBundle: false,
  bundleTitle: null,
  editCardType: "quiz",
  editCardNumber: -1,
  cardNo: 1,
  cardList: [],
};

// 2. Type에 따른 state 변화 설정 && state가 변화할 때 수행되는 함수
const makeCardReducer = (state = initialState, action) => {
  switch (action.type) {
    case "ADD_CARD": {
      return {
        // spread 연산자를 이용하여 기존 객체를 불러옴
        ...state,
        cardList: [...state.cardList, { ...action.payload }],
        cardNo: state.cardNo + 1,
      };
    }
    case "INIT_CARD_NO": {
      return {
        ...state,
        editCardNumber: -1,
      };
    }
    case "EDIT_CARD_NO": {
      return {
        ...state,
        editCardNumber: action.payload,
      };
    }
    case "EDIT_CARD": {
      const editCardNo = action.payload.editNumber;
      const editCard = action.payload.card;
      return {
        ...state,
        cardList: [...state.cardList.filter((_, index) => index !== editCardNo), editCard],
      };
    }
    case "DELETE_CARD": {
      const deleteCardIndex = action.payload;
      return {
        // spread 연산자를 이용하여 기존 객체를 불러옴
        ...state,
        // cardList: state.cardList.filter((card) => card.cardno !== deleteCardNo),
        cardList: state.cardList.filter((_, index) => index !== deleteCardIndex),
      };
    }
    case "CARD_STORE_RESET": {
      return {
        ...state,
        isBundle: false,
        bundleTitle: null,
        editCardType: "quiz",
        editCardNumber: -1,
        cardNo: 1,
        cardList: [],
      };
    }
    default:
      return state;
  }
};

export default makeCardReducer;

// testData: [...state.testData, { ...action.payload }],
// testDataL state.testDate.concat(action.payload),

// default: {
//   return { state };
// }

/*
  ...state의 ... 의 의미는 변화된 state만을 받는다는 의미이다. 즉, state에 변화가 없다면 기존값 그대로 반환
  마지막 default: return state;는 필수
*/
