import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../css/manager/returns.css";

function Returns() {
  const [returnLogs, setReturnLogs] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [editData, setEditData] = useState({ fuelLevel: "", returnDate: "" });
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchReturnLogs();
  }, []);

  const fetchReturnLogs = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/returnlog/all", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
      setReturnLogs(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  const handleEditClick = (log) => {
    setEditingId(log.id);
    setEditData({ fuelLevel: log.fuelLevel, returnDate: log.returnDate });
  };

  const handleCancelEdit = () => {
    setEditingId(null);
    setEditData({ fuelLevel: "", returnDate: "" });
  };

  const handleUpdate = async (id) => {
    try {
      await axios.put(
        `http://localhost:8080/api/returnlog/update/${id}`,
        editData,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
            "Content-Type": "application/json",
          },
        }
      );
      setMsg("Return Log updated successfully");
      setEditingId(null);
      fetchReturnLogs();
    } catch (error) {
      console.error("Update failed:", error);
      setMsg("Update failed");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this return log?")) return;

    try {
      await axios.delete(`http://localhost:8080/api/returnlog/delete/${id}`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
      setMsg("Return Log deleted successfully");
      fetchReturnLogs();
    } catch (error) {
      console.error("Delete failed:", error);
      setMsg("Delete failed");
    }
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
                Return Logs
              </li>
            </ol>
          </nav>
        </div>
      </div>

      <h2 className="text-center fanta-heading mb-4">Return Logs</h2>

      {msg && <div className="alert alert-info text-center">{msg}</div>}

      <div className="text-center mb-4">
        <button className="btn btn-fanta" onClick={() => navigate("/manager/add-return-log")}>
          Add Return Log
        </button>
      </div>

      <div className="row">
        {returnLogs.map((log) => (
          <div className="col-md-4 mb-4" key={log.id}>
            <div className="card car-card shadow-sm">
              <div className="card-body">
                <h5 className="card-title text-fanta">Return Log #{log.id}</h5>

                {editingId === log.id ? (
                  <>
                    <div className="mb-2">
                      <label className="form-label">Fuel Level</label>
                      <input
                        type="text"
                        className="form-control"
                        value={editData.fuelLevel}
                        onChange={(e) =>
                          setEditData({ ...editData, fuelLevel: e.target.value })
                        }
                      />
                    </div>
                    <div className="mb-2">
                      <label className="form-label">Return Date</label>
                      <input
                        type="date"
                        className="form-control"
                        value={editData.returnDate}
                        onChange={(e) =>
                          setEditData({ ...editData, returnDate: e.target.value })
                        }
                      />
                    </div>
                    <div className="d-flex justify-content-end gap-2">
  <button
    className="btn btn-sm text-white"
    style={{ backgroundColor: "#ff6f00", border: "none" }}
    onClick={() => handleUpdate(log.id)}
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
                      <strong>Booking ID:</strong> {log.bookingId}<br />
                      <strong>Car Brand:</strong> {log.carName}<br />
                      <strong>Fuel Level:</strong> {log.fuelLevel}<br />
                      <strong>Return Date:</strong> {log.returnDate}<br />
                    </p>
                    <div className="d-flex justify-content-end gap-2">
                      <button className="btn btn-sm btn-outline-warning" onClick={() => handleEditClick(log)}>
                        Edit
                      </button>
                      <button className="btn btn-sm btn-outline-danger" onClick={() => handleDelete(log.id)}>
                        Delete
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

export default Returns;
