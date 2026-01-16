import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import "./css/Login.css";
import { useDispatch } from "react-redux";
import { setUserDetails } from "../store/actions/UserAction";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const processLogin = async () => {
    let encodeString = window.btoa(username + ":" + password);
    try {
      const response = await axios.get("http://localhost:8080/api/user/token", {
        headers: { Authorization: "Basic " + encodeString },
      });

      let token = response.data.token;
      localStorage.setItem("token", token);

      let details = await axios.get("http://localhost:8080/api/user/details", {
        headers: { Authorization: "Bearer " + token },
      });
console.log("User Details Response: ", details.data);
      let user = {
        username: username,
        role: details.data.user.role,
      };
      setUserDetails(dispatch)(user);

      let name = details.data.name;
      localStorage.setItem("name", name);
      let role = details.data.user.role;

      switch (role) {
        case "CUSTOMER":
          navigate("/customer");
          break;
        case "CAROWNER":
          navigate("/carowner");
          break;
        case "MANAGER":
          navigate("/manager");
          break;
        default:
          setMsg("Login Disabled, Contact Admin at admin@example.com");
          return;
      }

      setMsg("Login Success !!");
    } catch (err) {
      setMsg("Invalid Credentials");
    }
  };

  return (
    <div className="container login-container">
      <div className="row justify-content-center">
        <div className="col-md-6 login-card">
          <div className="text-center mt-3 mb-4">
             <h2 className="platform-title"><strong>RoadReady !!!</strong></h2>
            <p className="platform-tagline">"Travel Better, Drive Smarter"</p>
          </div>

          <div className="card-header login-header">Login</div>
          <div className="card-body">
            {msg && (
              <div
                className={`alert ${
                  msg.includes("Success") ? "alert-success" : "alert-danger"
                } login-alert`}
              >
                {msg}
              </div>
            )}

            <div className="mb-2">
              <label className="login-label">Username:</label>
              <input
                type="text"
        className="form-control"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>
      <div className="mb-2">
              <label className="login-label">Password:</label>
              <input
                type="password"
            className="form-control"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
            <div className="mb-2">
         <button className="btn login-btn" onClick={processLogin}>
                Login
              </button>
            </div>
          </div>
          <div className="card-footer login-footer">
            Don't have an account? <Link to="/signup">Sign Up here.</Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
