import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import "./index.css";
import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";
import Hyeji from "assets/images/bundler/이혜지.jpeg";
import Hyojin from "assets/images/bundler/강효진.jpeg";
import Youngsik from "assets/images/bundler/김영식.jpeg";
import Taeyoon from "assets/images/bundler/안태윤.jpeg";
import Sungjoon from "assets/images/bundler/임성준.jpeg";
import SeKwun from "assets/images/bundler/정세권.jpeg";

function A810() {
  const navigate = useNavigate();
  const navigateToPurchase = () => {
    navigate("/home");
  };

  return (
    <DashboardLayout>
      <ul className="cards">
        <li>
          <a href="https://github.com/leehyeji319" className="card">
            <img src={Hyeji} className="card__image" alt="" />
            <div className="card__overlay">
              <div className="card__header">
                <div className="card__header-text">
                  <h3 className="card__title">이혜지</h3>
                  <span className="card__status">조장 / 백엔드 / API 공장장</span>
                </div>
              </div>
              <p className="card__description">네 코드에 cheers~</p>
            </div>
          </a>
        </li>
        <li>
          <a href="https://github.com/KangHyojin1401" className="card">
            <img src={Hyojin} className="card__image" alt="" />
            <div className="card__overlay">
              <div className="card__header">
                <div className="card__header-text">
                  <h3 className="card__title">강효진</h3>
                  <span className="card__status">백엔드팀장 / 회원관리 마스터</span>
                </div>
              </div>
              <p className="card__description">될때까지 달려~ </p>
            </div>
          </a>
        </li>
        <li>
          <a href="https://github.com/zer0eat" className="card">
            <img src={Youngsik} className="card__image" alt="" />
            <div className="card__overlay">
              <div className="card__header">
                <div className="card__header-text">
                  <h3 className="card__title">김영식</h3>
                  <span className="card__status">프로젝트매니저 / 프론트엔드</span>
                </div>
              </div>
              <p className="card__description">비선실세 : 일해라 팀원들아</p>
            </div>
          </a>
        </li>
        <li>
          <a href="https://github.com/anTuni" className="card">
            <img src={Taeyoon} className="card__image" alt="" />
            <div className="card__overlay">
              <div className="card__header">
                <div className="card__header-text">
                  <h3 className="card__title">안태윤</h3>
                  <span className="card__status">인프라 / 백엔드 / 배포의 신</span>
                </div>
              </div>
              <p className="card__description">안녕하세요. zi태윤zon 입니다. 인기도 사요 </p>
            </div>
          </a>
        </li>
        <li>
          <a href="https://github.com/sssungjooon" className="card">
            <img src={Sungjoon} className="card__image" alt="" />
            <div className="card__overlay">
              <div className="card__header">
                <div className="card__header-text">
                  <h3 className="card__title">임성준</h3>
                  <span className="card__status">UCC팀장 / 프론트엔드 / 통계의 마술사</span>
                </div>
              </div>
              <p className="card__description">리액트에 거침없이 하이킥 </p>
            </div>
          </a>
        </li>
        <li>
          <a href="https://github.com/jjungsk" className="card">
            <img src={SeKwun} className="card__image" alt="" />
            <div className="card__overlay">
              <div className="card__header">
                <div className="card__header-text">
                  <h3 className="card__title">정세권</h3>
                  <span className="card__status">프론트엔드 팀장 / 리액트의 아버지</span>
                </div>
              </div>
              <p className="card__description">리액트에 Hook을 갈겨야지^^ </p>
            </div>
          </a>
        </li>
      </ul>
      <div className="buttons">
        <Button
          onClick={navigateToPurchase}
          id="bundlerBtn"
          className="learn-more2"
          sx={{
            marginTop: "4%",
            bgcolor: "#81D8CF",
            color: "#000000",
            fontSize: "large",
            fontWeight: "bold",
            width: "250px",
          }}
          type="submit"
        >
          홈으로 돌아가기
        </Button>
      </div>
    </DashboardLayout>
  );
}

export default A810;
