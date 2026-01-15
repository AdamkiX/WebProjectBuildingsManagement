import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";

// Axios instance
const axiosInstance = axios.create({
	withCredentials: true,
	baseURL: "http://localhost:8080",
});

// Response interceptor
axiosInstance.interceptors.response.use(
	(response: AxiosResponse) => response,

	async (error) => {
		const originalRequest = error.config as AxiosRequestConfig & {
			_retry: boolean;
		};

		if (error.response?.status === 401 && !originalRequest._retry) {
			originalRequest._retry = true;

			try {
				const response = await axiosInstance.post(
					"/auth/refresh",
					{},
					{
						withCredentials: true,
					}
				);

				return axiosInstance(originalRequest);
			} catch (refreshError) {
				return Promise.reject(refreshError);
			}
		}
		return Promise.reject(error);
	}
);

export default axiosInstance;
