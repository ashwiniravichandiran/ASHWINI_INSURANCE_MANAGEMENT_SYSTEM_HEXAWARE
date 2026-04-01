

import { Link } from "react-router-dom";
import { Car, Bike, Caravan, CheckCircle2, ArrowRight } from "lucide-react";
import carImg from "../assets/car2.jpg"
import bikeImg from "../assets/bike.jpg";
import vanImg from "../assets/van.jpg";

function Policies() {
  const policies = [
    {
      icon: Car,
      title: "Car Insurance",
      image: carImg,
      desc: "Comprehensive coverage for your four-wheeler with roadside assistance and zero depreciation.",
      features: ["Third-party liability", "Own damage cover", "Roadside assistance"],
      addons: ["Zero depreciation", "Engine protector"],
    },
    {
      icon: Bike,
      title: "Bike Insurance",
      image: bikeImg,
      desc: "Affordable protection for your two-wheeler against theft, accidents, and natural disasters.",
      features: ["Accident cover", "Theft protection", "Natural calamity"],
      addons: ["Pillion cover", "Accessories cover"],
    },
    {
      icon: Caravan,
      title: "Camper Van Insurance",
      image: vanImg,
      desc: "Specialized coverage for recreational vehicles with contents and travel protection.",
      features: ["Contents cover", "Travel liability", "Breakdown cover"],
      addons: ["Personal belongings", "European travel"],
    },
  ];

  return (
    <section className="py-5 bg-light">
      <div className="container">

        <div className="text-center mb-5">
          <h2 className="fw-bold">Choose Your Coverage</h2>
          <p className="text-muted">Tailored plans for every type of vehicle</p>
        </div>

        <div className="row">
          {policies.map((p, i) => (
            <div key={i} className="col-md-4 mb-4">
              <div className="card text-white border-0 shadow">

                <div
                  className="card-body d-flex flex-column justify-content-start"
                  style={{
                    backgroundImage: `linear-gradient(rgba(97, 88, 88, 0.6), rgba(0, 0, 0, 0.85)), url(${p.image})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center",
                    minHeight: "380px",
                    borderRadius: "10px",
                  }}
                >

                  <div className="mb-3">
                    <p.icon size={30} />
                  </div>

                  
                  <h5 className="fw-bold">{p.title}</h5>

                  <p className="small">{p.desc}</p>

                  <div className="mb-3">
                    {p.features.map((f, index) => (
                      <div key={index} className="d-flex align-items-center mb-1">
                        <CheckCircle2 size={16} className="me-2 text-success" />
                        <small>{f}</small>
                      </div>
                    ))}
                  </div>

                  <div className="mb-3">
                    {p.addons.map((a, index) => (
                      <span key={index} className="badge bg-secondary me-2 mb-1">
                        {a}
                      </span>
                    ))}
                  </div>

                  <Link to="/login" className="btn btn-outline-light mt-auto">
                    View Details <ArrowRight size={16} className="ms-1" />
                  </Link>

                </div>
              </div>
            </div>
          ))}
        </div>

      </div>
    </section>
  );
}

export default Policies;