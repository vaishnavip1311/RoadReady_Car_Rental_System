function Stats() {
    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4" style={{ color: "#ff6f00" }}>
                Your Rental Summary
            </h2>

            <div className="row">
          
                <div className="col-md-6 mb-3">
                    <div className="card shadow-sm p-3">
                        <h5 className="card-title text-secondary">Total Bookings</h5>
                        <p className="card-text display-6">8</p>
                    </div>
                </div>

               
                <div className="col-md-6 mb-3">
                    <div className="card shadow-sm p-3">
                        <h5 className="card-title text-secondary">Active Bookings</h5>
                        <p className="card-text display-6">2</p>
                    </div>
                </div>
            </div>

            <div className="row mt-4">
              
                <div className="col-md-6 mb-3">
                    <div className="card shadow-sm p-3">
                        <h5 className="card-title text-secondary">Completed Returns</h5>
                        <p className="card-text display-6">6</p>
                    </div>
                </div>

               
                <div className="col-md-6 mb-3">
                    <div className="card shadow-sm p-3">
                        <h5 className="card-title text-secondary">Total Amount Spent</h5>
                        <p className="card-text display-6">₹24,500</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Stats;
