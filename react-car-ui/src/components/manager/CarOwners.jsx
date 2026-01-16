import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../css/manager/Cars.css";

function CarOwners() {
  const [owners, setOwners] = useState([]);

  useEffect(() => {
    const fetchOwners = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/carowner/get-all", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setOwners(response.data);
      } catch (error) {
        console.error("Error fetching car owners:", error);
      }
    };
    fetchOwners();
  }, []);

  return (
    <>
      <div className="container mt-4">
        <nav aria-label="breadcrumb">
          <ol className="breadcrumb fanta-breadcrumb">
            <li className="breadcrumb-item">
              <Link to="/manager">Manager Dashboard</Link>
            </li>
            <li className="breadcrumb-item active" aria-current="page">
              Car Owners
            </li>
          </ol>
        </nav>
      </div>

      <h1 className="text-center fanta-heading mt-2">Car Owner List</h1>

      <div className="container mt-4">
        <div className="row">
          {owners.map((owner, index) => (
            <div className="col-md-4 mb-4" key={index}>
                <div className="card car-card shadow-sm">
                  <div className="card-body">
                  <h5 className="card-title text-fanta">
                    {owner.name} (ID: {owner.id})
                  </h5>
                  <p className="card-text">
                         <strong>Email:</strong> {owner.email} <br />
                    <strong>Phone:</strong> {owner.contact} <br />
                    <strong>Verified:</strong> {owner.verified ? "Yes" : "No"} <br />
                     
                  </p>
                  <div className="d-grid gap-2">
                    <Link
                      to={`/manager/owner-cars/${owner.id}`}
                      className="btn btn-fanta"
                    >
                      View Cars
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default CarOwners;
