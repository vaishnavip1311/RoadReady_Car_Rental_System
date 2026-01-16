import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../css/manager/CarMaintenance.css";

function Maintenance() {
  const [maintenances, setMaintenances] = useState([]);
  const [editId, setEditId] = useState(null);
  const [editForm, setEditForm] = useState({ type: "", date: "", remarks: "" });
  const navigate = useNavigate();

  useEffect(() => {
    fetchMaintenances();
  }, []);

  const fetchMaintenances = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/maintenance/all", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
      setMaintenances(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  const handleEditClick = (m) => {
    setEditId(m.id);
    setEditForm({
      type: m.type,
      date: m.date,
      remarks: m.remarks,
    });
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleUpdate = async (id) => {
    try {
      await axios.put(`http://localhost:8080/api/maintenance/update/${id}`, editForm, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
      setEditId(null);
      fetchMaintenances();
    } catch (err) {
      console.error("Update failed", err);
    }
  };

  const handleCancelEdit = () => {
    setEditId(null);
    setEditForm({ type: "", date: "", remarks: "" });
  };

  return (
    <div className="container mt-5">
      <div className="row mb-3">
        <div className="col-lg-12">
          <nav aria-label="breadcrumb">
            <ol className="breadcrumb fanta-breadcrumb">
              <li className="breadcrumb-item">
                <Link to="/manager">Manager Dashboard</Link>
              </li>
              <li className="breadcrumb-item active" aria-current="page">
                Maintenance List
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <h2 className="text-center fanta-heading mb-3">Maintenance List</h2>

      <div className="text-center mb-4">
        <button className="btn btn-fanta" onClick={() => navigate("/manager/add-maintenance")}>
          Add Maintenance
        </button>
      </div>

      <div className="row">
        {maintenances.map((m) => (
          <div className="col-md-4 mb-4" key={m.id}>
            <div className="card car-card shadow-sm">
              <div className="card-body">
                <h5 className="card-title text-fanta">Maintenance #{m.id}</h5>

                {editId === m.id ? (
                  <>
                    <div className="mb-2">
                      <label className="form-label">Type</label>
                      <input
                        name="type"
                        value={editForm.type}
                        onChange={handleEditChange}
                        className="form-control"
                      />
                    </div>
                    <div className="mb-2">
                      <label className="form-label">Date</label>
                      <input
                        type="date"
                        name="date"
                        value={editForm.date}
                        onChange={handleEditChange}
                        className="form-control"
                      />
                    </div>
                    <div className="mb-2">
                      <label className="form-label">Remarks</label>
                      <input
                        name="remarks"
                        value={editForm.remarks}
                        onChange={handleEditChange}
                        className="form-control"
                      />
                    </div>
                    <div className="d-flex justify-content-end gap-2">
                      <button
                        className="btn btn-sm text-white"
                        style={{ backgroundColor: "#ff6f00", border: "none" }}
                        onClick={() => handleUpdate(m.id)}
                      >
                        Save
                      </button>
                      <button
                        className="btn btn-sm text-white"
                        style={{ backgroundColor: "#ff6f00", border: "none" }}
                        onClick={handleCancelEdit}
                      >
                        Cancel
                      </button>
                    </div>
                  </>
                ) : (
                  <>
                    <p className="card-text">
                      <strong>Car ID:</strong> {m.carId} <br />
                      <strong>Car Name:</strong> {m.car} <br />
                      <strong>Type:</strong> {m.type} <br />
                      <strong>Date:</strong> {m.date} <br />
                      <strong>Remarks:</strong> {m.remarks} <br />
                    </p>
                    <div className="text-end">
                      <button
                        className="btn btn-sm text-white"
                        style={{ backgroundColor: "#ff6f00", border: "none" }}
                        onClick={() => handleEditClick(m)}
                      >
                        Update
                      </button>
                    </div>
                  </>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Maintenance;
