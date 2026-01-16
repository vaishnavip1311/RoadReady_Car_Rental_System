import axios from "axios";
import { useState, useEffect, useRef } from "react";
import { useNavigate, Link } from "react-router-dom";

function EditCustomerProfile() {
  const navigate = useNavigate();
  const fileInputRef = useRef(null);

  const [customer, setCustomer] = useState({
    name: "",
    contact: "",
    email: ""
  });

  const [msg, setMsg] = useState("");
  const [imgMsg, setImgMsg] = useState("");

  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/customer/get-one", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
          }
        });
        setCustomer(response.data);
      } catch (err) {
        console.error("Failed to fetch customer details", err);
      }
    };
    fetchCustomer();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCustomer((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put("http://localhost:8080/api/customer/update", customer, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });
      setMsg("Profile updated successfully!");
     
    } catch (err) {
      console.error("Update failed", err);
      setMsg("Update failed. Try again.");
    }
  };

  const handleImageUpload = async () => {
    const file = fileInputRef.current.files[0];
    if (!file) {
      setImgMsg("Please select a profile picture.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      await axios.post("http://localhost:8080/api/customer/upload/profile-pic", formData, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });

      setImgMsg("Profile picture uploaded successfully.");

      const updated = await axios.get("http://localhost:8080/api/customer/get-one", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });

      setCustomer(updated.data);
    } catch (err) {
      console.error("Image upload failed", err);
      setImgMsg("Failed to upload profile picture.");
    }
  };

  return (
    <div className="container mt-2 pt-2">
      <div className="row mb-1">
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item"><Link to="/customer">Customer Dashboard</Link></li>
              <li className="breadcrumb-item"><Link to="/customer/profile">Profile</Link></li>
              <li className="breadcrumb-item active" aria-current="page">Edit Profile</li>
            </ol>
          </nav>
        </div>
      </div>

      <div className="card shadow-sm col-md-8 offset-md-2">
        <div className="card-header text-center text-fanta">Edit Customer Profile</div>

        <div className="card-body">
          {msg && <div className="alert alert-success">{msg}</div>}

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Name</label>
              <input type="text" name="name" value={customer.name || ""} onChange={handleChange} className="form-control" required />
            </div>

            <div className="mb-3">
              <label className="form-label">Contact</label>
              <input type="text" name="contact" value={customer.contact || ""} onChange={handleChange} className="form-control" required />
            </div>

            <div className="mb-3">
              <label className="form-label">Email</label>
              <input type="email" name="email" value={customer.email || ""} onChange={handleChange} className="form-control" required />
            </div>

            <div className="mb-3">
              <label className="form-label">Upload Profile Picture</label>
              <input type="file" ref={fileInputRef} className="form-control" accept="image/*" />
              <button type="button" className="btn btn-fanta mt-2" onClick={handleImageUpload}>
                Upload Image
              </button>
              {imgMsg && <div className="alert alert-info mt-2">{imgMsg}</div>}
            </div>

            <div className="d-grid mt-3">
              <button type="submit" className="btn btn-fanta">Update Profile</button>
            </div>
          </form>
        </div>

        <div className="card-footer text-center text-muted">Customer Panel</div>
      </div>
    </div>
  );
}

export default EditCustomerProfile;
