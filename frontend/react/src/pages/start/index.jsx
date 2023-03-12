import { Link } from "react-router-dom";
import "./index.css";

// 시작 화면
function Start() {
  return (
    <div className="frame">
      {/* 클릭시 사용 화면으로 이동 */}
      <Link to="/login">
        <figure>
          <div className="container">
            <div className="image-1">
              <div className="carousel">
                <div className="carousel__face" />
                <div className="carousel__face" />
                <div className="carousel__face" />
                <div className="carousel__face" />
                <div className="carousel__face" />
                <div className="carousel__face" />
                <div className="carousel__face" />
                <div className="carousel__face" />
              </div>
            </div>
          </div>
          <div className="image-2" />
          <div className="image-2" />
          <div className="image-2" />
          <div className="image-2" />
          <div className="image-2" />
          <div className="image-2" />
          <div className="image-2" />
          <div className="image-2" />
          <div className="image-2" />
        </figure>
      </Link>
    </div>
  );
}

export default Start;
