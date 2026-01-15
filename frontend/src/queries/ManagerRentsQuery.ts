import axiosInstance from "@/providers/AxiosInstance";
import { Rent } from "@/types/Rents";
import { useQuery } from "@tanstack/react-query";

async function getRents() {
	const response = await axiosInstance.get<{ rents: Rent[] }>("/rents/all");
	return response.data;
}

export default function useRentsManager() {
	return useQuery({ queryKey: ["rentsManager"], queryFn: getRents });
}
