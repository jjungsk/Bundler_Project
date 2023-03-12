/*
  응답결과(데이터)를 정제 해주는 함수 정의
*/

const sortFeedList = (feedList) =>
  feedList.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

const listId = (feeds) => {
  const list = [];
  feeds.forEach((feed) =>
    feed.cardId !== undefined ? list.push(feed.cardId) : list.push(feed.bundleId)
  );
  return list;
};

export { sortFeedList, listId };
