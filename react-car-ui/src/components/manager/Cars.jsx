import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { fetchAllCars } from "../../store/actions/CarAction";
import "../css/manager/Cars.css";

function Cars() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const cars = useSelector(state => state.cars.cars);

  const [page, setPage] = useState(0);
  const [size] = useState(6); 


  useEffect(() => {
    const url = `http://localhost:8080/api/car/all?page=${page}&size=${size}`;
    fetchAllCars(dispatch)(url);
  }, [dispatch, page, size]);


  const handlePrevPage = () => {
    if (page > 0) setPage(page - 1);
  };

  const handleNextPage = () => {
    setPage(page + 1);
  };

  return (
    <>
      <div className="container-fluid">
        <div className="row">
          <div className="col-lg-12">

            <nav aria-label="breadcrumb">
              <ol className="breadcrumb fanta-breadcrumb">
                <li className="breadcrumb-item">
                  <Link to="/manager">Manager Dashboard</Link>
                </li>
                <li className="breadcrumb-item active" aria-current="page">Cars</li>
              </ol>
            </nav>

          </div>
        </div>

        <h1 className="text-center fanta-heading mt-2">Cars List</h1>

        <div className="container mt-3">
          <div className="row">
            {cars.map((c, index) => (
              <div className="col-md-4 mb-4" key={index}>
                <div className="card car-card shadow-sm">
                  <img
                    className="card-img-top"
                    src={`../images/${c.pic}`}
                    alt={`${c.brand} ${c.model}`}
                    style={{ height: "220px", objectFit: "cover", padding: "20px" }}
                  />
                  <div className="card-body">
                    <h5 className="card-title text-fanta">
                      {c.brand} {c.model} ({c.year})
                    </h5>
                    <p className="card-text">
                      <strong>Color:</strong> {c.colour} <br />
                      <strong>Fuel:</strong> {c.fuelType} <br />
                      <strong>Seats:</strong> {c.seats} <br />
                      <strong>Status:</strong> {c.availabilityStatus} <br />
                      <strong>Price/Day:</strong> ₹{c.pricePerDay}
                    </p>
                    <Link to={`/manager/car-details/${c.id}`} className="btn btn-fanta w-100">
                      Edit Details
                    </Link>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Pagination controls */}
        <div className="d-flex justify-content-center gap-3 mb-4 align-items-center">
          <button
            className="btn btn-link"
            disabled={page === 0}
            onClick={handlePrevPage}
            style={{ textDecoration: "none", fontSize: "1.5rem", color: "#ff6f00" }}
          >
            &lt;
          </button>
          <span style={{ fontWeight: "none", fontSize: "1.25rem" }}>{page + 1}</span>
          <button
            className="btn btn-link"
            onClick={handleNextPage}
            style={{ textDecoration: "none", fontSize: "1.5rem", color: "#ff6f00" }}
          >
            &gt;
          </button>
        </div>
      </div>
    </>
  );
}

export default Cars;
