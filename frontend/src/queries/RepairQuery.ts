import axios from "@/providers/AxiosInstance";
import { useQuery } from "@tanstack/react-query";

async function getRepairs() {
	const response = await axios.get("/repaircom");
	return response.data;
}

export default function useRepair() {
	return useQuery({
		queryKey: ["repairs"],
		queryFn: getRepairs,
	});
}
