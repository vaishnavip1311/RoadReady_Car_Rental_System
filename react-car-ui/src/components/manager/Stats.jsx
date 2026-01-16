import { useEffect, useState } from "react";
import { Chart } from 'primereact/chart';
import axios from "axios";

function Stats() {
    const [chartData, setChartData] = useState({});
    const [chartOptions, setChartOptions] = useState({});
    const [carNames, setCarNames] = useState([]);
    const [bookingCounts, setBookingCounts] = useState([]);

    useEffect(() => {
        const getStats = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/manager/booking/stats', {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    }
                });

                const { carNames, bookingCounts } = response.data;

                setCarNames(carNames);
                setBookingCounts(bookingCounts);

                const data = {
                    labels: carNames,
                    datasets: [
                        {
                            label: 'Bookings per Car',
                            data: bookingCounts,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255, 99, 132, 1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1
                        }
                    ]
                };

                const options = {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: 'top'
                        },
                        title: {
                            display: true,
                            text: 'Car Bookings Overview'
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                };

                setChartData(data);
                setChartOptions(options);
            } catch (err) {
                console.error("Failed to load stats", err);
            }
        };

        getStats();
    }, []);

    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold mb-4">Car Booking Stats</h1>
            <div style={{ width: '80%', margin: '0 auto' }}>
                <div className="card shadow-lg rounded-xl p-4">
                    <Chart type="bar" data={chartData} options={chartOptions} />
                </div>
            </div>
            <div className="mt-8">
                <h2 className="text-xl font-semibold mb-2">Cars:</h2>
                <ul className="list-disc pl-5">
                    {carNames.map((car, index) => (
                        <li key={index}>{car}: {bookingCounts[index]} bookings</li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default Stats;
