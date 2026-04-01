import http from "./http-common";

class AuthService {
  login(data) {
    return http.post("/user/login", data);
  }

  register(data) {
    return http.post("/user/add", data);
  }
}


const authService = new AuthService();

export default authService;