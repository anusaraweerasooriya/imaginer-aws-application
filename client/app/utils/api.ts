import axios from "axios";

const API_URL = "http://localhost:9090/api";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  return token ? { Authorization: `Bearer ${token}` } : {};
};

// Register a new user
export const registerUser = async (username: string, password: string) => {
  const response = await axios.post(`${API_URL}/auth/register`, { username, password });
  return response.data;
};

// Login user
export const loginUser = async (username: string, password: string) => {
  const response = await axios.post(`${API_URL}/auth/login`, { username, password });
  return response.data;
};

// Get all images
export const getAllImages = async () => {
  const response = await axios.get(`${API_URL}/uploads/images`);
  return response.data;
};

// Get user images (protected route)
export const getUserImages = async () => {
  const response = await axios.get(`${API_URL}/uploads/user-images`, {
    headers: getAuthHeader(), // Add token to the request
  });
  return response.data;
};

// Upload an image (protected)
export const uploadImage = async (file: File) => {
  const formData = new FormData();
  formData.append("file", file);

  const response = await axios.post(`${API_URL}/uploads/image`, formData, {
    headers: {
      ...getAuthHeader(),
      "Content-Type": "multipart/form-data",
    },
  });

  return response.data;
};
