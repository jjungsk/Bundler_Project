// Import - react basic
import React from "react";
import PropTypes from "prop-types";

import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import Grid from "@mui/material/Grid";
import cardRabbit from "assets/images/bundler/bundler_rabbit_1.png";
import CardThumbnailCard from "../components/thumCard/ThumnailCard";

// BundlelistTabForm Template
function CardListTab({ data }) {
  return (
    <MDBox
      sx={{
        marginLeft: "3%",
        marginTop: "7%",
        marginRight: "3%",
      }}
    >
      {data.length === 0 ? (
        <MDBox
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "center",
            height: "700px",
          }}
        >
          <MDBox // 토끼사진과 카드 없다는 글자
            sx={{
              display: "flex",
              flexDirection: "column",
              marginTop: "50px",
            }}
          >
            <MDBox // 토끼사진만
              sx={{ display: "flex", justifyContent: "center" }}
            >
              <img src={cardRabbit} alt="noBundle" style={{ height: "200px", width: "200px" }} />
            </MDBox>
            <MDTypography fontSize="50px" mt={1}>
              아직 작성한 카드가 없어요!
            </MDTypography>
          </MDBox>
        </MDBox>
      ) : (
        <Grid container spacing={3}>
          {data.map((infoCard) => (
            <Grid item xs={12} md={6} lg={3} key={infoCard.cardId}>
              <CardThumbnailCard infoCard={infoCard} />
              {/* {oneCard.secondCategoryName === "기타" ? (
                <CardThumbnailCard
                  cardId={oneCard.cardId}
                  cardType={oneCard.cardType}
                  cardTitle={oneCard.feedTitle}
                  cardLike={oneCard.feedLikeCnt}
                  cardScrap={oneCard.cardScrapCnt}
                  CategoryName={oneCard.firstCategoryName}
                />
              ) : (
                <CardThumbnailCard
                  cardId={oneCard.cardId}
                  cardType={oneCard.cardType}
                  cardTitle={oneCard.feedTitle}
                  cardLike={oneCard.feedLikeCnt}
                  cardScrap={oneCard.cardScrapCnt}
                  CategoryName={oneCard.secondCategoryName}
                />
              )} */}
            </Grid>
          ))}
        </Grid>
      )}
    </MDBox>
  );
}

CardListTab.defaultProps = {
  data: [],
};

CardListTab.propTypes = {
  data: PropTypes.shape(PropTypes.arrayOf),
};

export default CardListTab;
