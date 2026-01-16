import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchAllCars } from "../../store/actions/CarAction";
import CarCard from "../../components/CarCard";

function AvailableCars() {
  const dispatch = useDispatch();
  const cars = useSelector((state) => state.cars.cars);

  const [page, setPage] = useState(0);
  const [size] = useState(6);
  const [minPrice, setMinPrice] = useState("");
  const [maxPrice, setMaxPrice] = useState("");
  const [availability, setAvailability] = useState("all");

  useEffect(() => {
    fetchAllCars(dispatch)(buildUrl());
  }, [dispatch, page, size, minPrice, maxPrice, availability]);

  const buildUrl = () => {
    const base = "http://localhost:8080/api/car";
    const priceFilter = minPrice !== "" && maxPrice !== "";

    if (availability === "available" && priceFilter) {
      return `${base}/search/price-and-availability?min=${minPrice}&max=${maxPrice}&page=${page}&size=${size}`;
    } else if (availability === "available") {
      return `${base}/available?page=${page}&size=${size}`;
    } else if (priceFilter) {
      return `${base}/search/price?min=${minPrice}&max=${maxPrice}&page=${page}&size=${size}`;
    } else {
      return `${base}/all?page=${page}&size=${size}`;
    }
  };

  const handlePrevPage = () => {
    if (page > 0) setPage(page - 1);
  };

  const handleNextPage = () => {
    setPage(page + 1);
  };

  return (
    <>
      <h2 className="text-center fanta-heading mb-4">Available Cars</h2>

      <div className="row mb-4">
        <div className="col-md-4">
          <label>Min Price</label>
          <input
            type="number"
            className="form-control"
            value={minPrice}
            onChange={(e) => setMinPrice(e.target.value)}
            placeholder="Min Price"
          />
        </div>
        <div className="col-md-4">
          <label>Max Price</label>
          <input
            type="number"
            className="form-control"
            value={maxPrice}
            onChange={(e) => setMaxPrice(e.target.value)}
            placeholder="Max Price"
          />
        </div>
        <div className="col-md-4">
          <label>Availability</label>
          <select
            className="form-select form-control"
            value={availability}
            onChange={(e) => setAvailability(e.target.value)}
          >
            <option value="all">All</option>
            <option value="available">Available Only</option>
          </select>
        </div>
      </div>

      <div className="row">
        {cars.map((car, index) => (
          <CarCard key={index} car={car} />
        ))}
      </div>

      <div className="d-flex justify-content-center gap-3 mb-4 align-items-center">
        <button
          className="btn btn-link"
          disabled={page === 0}
          onClick={handlePrevPage}
          style={{ textDecoration: "none", fontSize: "1.5rem", color: "#ff6f00" }}
        >
          &lt;
        </button>
        <span style={{ fontSize: "1.25rem" }}>{page + 1}</span>
        <button
          className="btn btn-link"
          onClick={handleNextPage}
          style={{ textDecoration: "none", fontSize: "1.5rem", color: "#ff6f00" }}
        >
          &gt;
        </button>
      </div>
    </>
  );
}

export default AvailableCars;

































// import { useEffect, useState } from "react";
// import { useNavigate } from "react-router-dom";
// import { useDispatch, useSelector } from "react-redux";
// import { fetchAllCars } from "../../store/actions/CarAction";
// import CarCard from "../../components/CarCard";

// function AvailableCars() {
//   const navigate = useNavigate();
//   const dispatch = useDispatch();

//   const cars = useSelector((state) => state.cars.cars);
//   const [page, setPage] = useState(0);
//   const [size] = useState(6);
//   const [minPrice, setMinPrice] = useState("");
//   const [maxPrice, setMaxPrice] = useState("");
//   const [availability, setAvailability] = useState("all");

//   useEffect(() => {
//     fetchAllCars(dispatch)(buildUrl());
//   }, [dispatch, page, size, minPrice, maxPrice, availability]);

//   const buildUrl = () => {
//     const base = "http://localhost:8080/api/car";
//     const priceFilter = minPrice !== "" && maxPrice !== "";

//     if (availability === "available" && priceFilter) {
//       return `${base}/search/price-and-availability?min=${minPrice}&max=${maxPrice}&page=${page}&size=${size}`;
//     } else if (availability === "available") {
//       return `${base}/available?page=${page}&size=${size}`;
//     } else if (priceFilter) {
//       return `${base}/search/price?min=${minPrice}&max=${maxPrice}&page=${page}&size=${size}`;
//     } else {
//       return `${base}/all?page=${page}&size=${size}`;
//     }
//   };

 
//   const handlePrevPage = () => {
//     if (page > 0) setPage(page - 1);
//   };

//   const handleNextPage = () => {
//     setPage(page + 1);
//   };

//   return (
//     <>
//       <h2 className="text-center fanta-heading mb-4">Available Cars</h2>

//       <div className="row mb-4">
//         <div className="col-md-4">
//           <label>Min Price</label>
//           <input
//             type="number"
//             className="form-control"
//             value={minPrice}
//             onChange={(e) => setMinPrice(e.target.value)}
//             placeholder="Min Price"
//           />
//         </div>
//         <div className="col-md-4">
//           <label>Max Price</label>
//           <input
//             type="number"
//             className="form-control"
//             value={maxPrice}
//             onChange={(e) => setMaxPrice(e.target.value)}
//             placeholder="Max Price"
//           />
//         </div>
//         <div className="col-md-4">
//           <label>Availability</label>
//           <select
//             className="form-select form-control"
//             value={availability}
//             onChange={(e) => setAvailability(e.target.value)}
//           >
//             <option value="all">All</option>
//             <option value="available">Available Only</option>
//           </select>
//         </div>
       
//       </div>

//       <div className="row">
//         {cars.map((c, index) => (
//           <div className="col-md-4 mb-4" key={index}>
//             <div className="card car-card shadow-sm">
//               <img
//                 className="card-img-top"
//                 src={`/images/${c.pic}`}
//                 alt={`${c.brand} ${c.model}`}
//                 style={{ height: "220px", objectFit: "cover", padding: "20px" }}
//               />
//               <div className="card-body">
//                 <h5 className="card-title text-fanta">
//                   {c.brand} {c.model} ({c.year})
//                 </h5>
//                 <p className="card-text">
//                   <strong>Color:</strong> {c.colour} <br />
//                   <strong>Fuel:</strong> {c.fuelType} <br />
//                   <strong>Seats:</strong> {c.seats} <br />
//                   <strong>Status:</strong>{" "}
//                   <span
//                     className={
//                       (c.availabilityStatus || "").toLowerCase() === "available"
//                         ? "text-success"
//                         : "text-danger"
//                     }
//                   >
//                     {c.availabilityStatus}
//                   </span>
//                   <br />
//                   <strong>Price/Day:</strong> ₹{c.pricePerDay}
//                 </p>

//                 <div className="d-flex gap-2">
//                   <button
//                     className="btn btn-outline-warning flex-grow-1"
//                     onClick={() => navigate(`/customer/car-reviews/${c.id}`)}
//                   >
//                     Reviews
//                   </button>

//                   <button
//                     className="btn btn-fanta flex-grow-1"
//                     disabled={(c.availabilityStatus || "").toLowerCase() !== "available"}
//                     onClick={() => navigate(`/customer/book/${c.id}`)}
//                   >
//                     Book Now
//                   </button>
//                 </div>
//               </div>
//             </div>
//           </div>
//         ))}
//       </div>

//       <div className="d-flex justify-content-center gap-3 mb-4 align-items-center">
//         <button
//           className="btn btn-link"
//           disabled={page === 0}
//           onClick={handlePrevPage}
//           style={{ textDecoration: "none", fontSize: "1.5rem", color: "#ff6f00" }}
//         >
//           &lt;
//         </button>
//         <span style={{ fontWeight: "none", fontSize: "1.25rem" }}>{page + 1}</span>
//         <button
//           className="btn btn-link"
//           onClick={handleNextPage}
//           style={{ textDecoration: "none", fontSize: "1.5rem", color: "#ff6f00" }}
//         >
//           &gt;
//         </button>
//       </div>
//     </>
//   );
// }

// export default AvailableCars;
