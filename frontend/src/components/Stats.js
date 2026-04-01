import React from "react";

function Stats() {
  const data = [
    { title: "Policies Active", value: "1200+" },
    { title: "Claims Processed", value: "850+" },
    { title: "Customers", value: "2000+" },
    { title: "Satisfaction", value: "98%" },
  ];

  return (
<div className="container py-4">
      <div className="row text-center">
        {data.map((item, index) => (
          <div key={index} className="col-md-3 mb-3">
            <div className="card shadow-sm">
              <div className="card-body">
                <h4 className="fw-bold">{item.value}</h4>
                <p className="text-muted">{item.title}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Stats;