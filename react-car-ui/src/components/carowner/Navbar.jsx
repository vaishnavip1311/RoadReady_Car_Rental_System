import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setUserDetails } from "../../store/actions/UserAction";

function Navbar() {
  const userFromStore = useSelector((state) => state.user);
  const [user] = useState(userFromStore);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const logout = () => {
    let u = {
      username: "",
      role: ""
    };
    setUserDetails(dispatch)(u);
    localStorage.clear();
    navigate("/");
  };

  return (
    <nav className="navbar navbar-light bg-light justify-content-between px-4 shadow-sm" style={{ height: "70px" }}>
      <div>
        <h2 className="platform-title m-0" style={{ color: "#ff6f00" }}>
          <strong>RoadReady !!!</strong>
        </h2>
        <p className="platform-tagline m-0" style={{ fontSize: "0.9rem", color: "#ff6f00" }}>
          "Travel Better, Drive Smarter"
        </p>
      </div>

      <div className="d-flex align-items-center">
        <span className="me-4 fw-bold text-dark" style={{ fontSize: "1.1rem" }}>
          Welcome {user?.username} !
        </span>
        <button
          className="btn"
          style={{ backgroundColor: "#ff6f00", color: "white" }}
          onMouseOver={(e) => (e.target.style.backgroundColor = "#e65c00")}
          onMouseOut={(e) => (e.target.style.backgroundColor = "#ff6f00")}
          onClick={logout}
        >
          Logout
        </button>
      </div>
    </nav>
  );
}

export default Navbar;
