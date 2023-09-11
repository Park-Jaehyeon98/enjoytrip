import axios from "axios";

// axios 객체 생성
function apiInstance() {
  const instance = axios.create({
    baseURL: "http://localhost:80/enjoytrip",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export default apiInstance;
