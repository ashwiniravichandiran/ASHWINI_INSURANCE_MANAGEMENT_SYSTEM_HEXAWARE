import { Star } from "lucide-react";

function Reviews() {
  const reviews = [
    {
      name: "Priya Sharma",
      role: "Car Owner",
      text: "Hassle-free claim settlement. Got my car repaired within a week without any issues. Highly recommended.",
      rating: 5,
    },
    {
      name: "Raj Malhotra",
      role: "Bike Rider",
      text: "Affordable premium and great coverage. The online process was simple and I got my policy in minutes.",
      rating: 5,
    },
    {
      name: "Anita Deshmukh",
      role: "Camper Van Owner",
      text: "Finally found insurance that actually understands recreational vehicles. The support team was incredibly helpful.",
      rating: 4,
    },
  ];

  return (
    <section className="py-5 bg-light">
      <div className="container">

        <div className="text-center mb-5">
          <h2 className="fw-bold">What Our Customers Say</h2>
          <p className="text-muted">Real experiences from real policyholders</p>
        </div>

        <div className="row">
          {reviews.map((r, i) => (
            <div key={i} className="col-md-4 mb-4">
              <div className="card h-100 shadow-sm border-0 p-4">

                <div className="mb-3 d-flex">
                  {Array.from({ length: 5 }).map((_, si) => (
                    <Star
                      key={si}
                      size={16}
                      className="me-1"
                      style={{
                        fill: si < r.rating ? "#ffc107" : "none",
                        color: "#ffc107",
                      }}
                    />
                  ))}
                </div>

                <p className="small text-muted">"{r.text}"</p>

                <div className="mt-3 border-top pt-3">
                  <p className="fw-semibold mb-0">{r.name}</p>
                  <small className="text-muted">{r.role}</small>
                </div>

              </div>
            </div>
          ))}
        </div>

      </div>
    </section>
  );
}

export default Reviews;