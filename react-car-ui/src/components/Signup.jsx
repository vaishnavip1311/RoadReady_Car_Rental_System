import { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import "./css/Login.css";

function Signup() {
  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("CUSTOMER");
  const [msg, setMsg] = useState("");
  const [email, setEmail] = useState("");
const [contact, setContact] = useState("");


  const navigate = useNavigate();

  const handleSignup = async () => {
   
   const payload = {
  name: name,
  email: email,
  contact: contact,
  user: {
    username: username,
    password: password
  }
};


    let endpoint = "";
    if (role === "CUSTOMER") {
      endpoint = "http://localhost:8080/api/customer/add";
    } else if (role === "CAROWNER") {
      endpoint = "http://localhost:8080/api/carowner/add";
    } 

    try {
      await axios.post(endpoint, payload, {
        headers: {
          "Content-Type": "application/json"
        }
      });

      setMsg("Signup successful! Redirect to login...");
     
    } catch (err) {
      console.error("Signup failed:", err);
      setMsg("Signup failed. Try with different credentials.");
    }
  };

  return (
    <div className="container login-container">
      <div className="row justify-content-center">
        <div className="col-md-6 login-card">
          <div className="text-center mt-3 mb-4">
            <h2 className="platform-title"><strong>RoadReady</strong></h2>
            <p className="platform-tagline">"Travel Better, Drive Smarter"</p>
          </div>

          <div className="card-header login-header">Sign Up</div>
          <div className="card-body">
            {msg && (
              <div
                className={`alert ${
                  msg.includes("successful") ? "alert-success" : "alert-danger"
                } login-alert`}
              >
                {msg}
              </div>
            )}

            <div className="mb-2">
              <label className="login-label">Name:</label>
              <input
                type="text"
                className="form-control"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>

            <div className="mb-2">
              <label className="login-label">Username:</label>
              <input
                type="text"
                className="form-control"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>

            <div className="mb-2">
              <label className="login-label">Password:</label>
              <input
                type="password"
                className="form-control"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <div className="mb-2">
              <label className="login-label">Role:</label>
              <select
                className="form-control"
                value={role}
                onChange={(e) => setRole(e.target.value)}
              >
                <option value="CUSTOMER">Customer</option>
                <option value="CAROWNER">Car Owner</option>
                
              </select>
            </div>
            <div className="mb-2">
  <label className="login-label">Email:</label>
  <input
    type="email"
    className="form-control"
    value={email}
    onChange={(e) => setEmail(e.target.value)}
    required
  />
</div>

<div className="mb-2">
  <label className="login-label">Contact:</label>
  <input
    type="text"
    className="form-control"
    value={contact}
    onChange={(e) => setContact(e.target.value)}
    required
  />
</div>


            <div className="mb-2">
              <button className="btn login-btn" onClick={handleSignup}>
                Sign Up
              </button>
            </div>
          </div>
          <div className="card-footer login-footer">
            Already have an account? <Link to="/login">Login here.</Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Signup;
