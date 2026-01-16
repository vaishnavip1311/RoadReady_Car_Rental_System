import axios from "axios";
import { useState, useEffect, useRef } from "react";
import { useNavigate, Link } from "react-router-dom";

function EditProfile() {
  const navigate = useNavigate();
  const fileInputRef = useRef(null);

  const [manager, setManager] = useState({
    name: "",
    contact: "",
    email: ""
  });

  const [msg, setMsg] = useState("");
  const [imgMsg, setImgMsg] = useState("");

  useEffect(() => {
    const fetchManager = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/manager/view", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
          }
        });
        setManager(response.data);
      } catch (err) {
        console.error("Failed to fetch manager details", err);
      }
    };
    fetchManager();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setManager((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put("http://localhost:8080/api/manager/update", manager, {
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
    await axios.post("http://localhost:8080/api/upload/profile-pic", formData, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    });

    setImgMsg("Profile picture uploaded successfully.");

    const updated = await axios.get("http://localhost:8080/api/manager/view", {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    });

    setManager(updated.data); 
  } catch (err) {
    console.error("Image upload failed", err);
    setImgMsg("Failed to upload profile picture.");
  }
};


  return (
    <div className="container mt-4">
      <div className="row mb-3">
  
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
   
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item"><Link to="/manager">Manager Dashboard</Link></li>
        
              <li className="breadcrumb-item"><Link to="/manager/profile">Profile</Link></li>
              <li className="breadcrumb-item active" aria-current="page">Edit Profile</li>
     
            </ol>
          </nav>
        </div>
      </div>

      <div className="card shadow-sm col-md-8 offset-md-2">
        <div className="card-header text-center text-fanta">Edit Manager Profile</div>
    
        <div className="card-body">
          {msg && <div className="alert alert-success">{msg}</div>}

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Name</label>
       
              <input type="text" name="name" value={manager.name} onChange={handleChange} className="form-control" required />
            </div>

            <div className="mb-3">
       
              <label className="form-label">Contact</label>
              <input type="text" name="contact" value={manager.contact} onChange={handleChange} className="form-control" required />
            </div>

            <div className="mb-3">
              <label className="form-label">Email</label>
          
              <input type="email" name="email" value={manager.email} onChange={handleChange} className="form-control" required />
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

        <div className="card-footer text-center text-muted">Manager Panel</div>
      </div>
    </div>
  );
}

export default EditProfile;
