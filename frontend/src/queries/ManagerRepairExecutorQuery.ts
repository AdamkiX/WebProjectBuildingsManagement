import axios from "@/providers/AxiosInstance";
import { RepiarManagerExecutorGet } from "@/types/Repairs";
import { useQuery } from "@tanstack/react-query";

async function getRepairs() {
	const response = await axios.get<{ repairs: RepiarManagerExecutorGet[] }>(
		"/managers/repair"
	);
	console.log(response.data);
	return response.data;
}

export default function useManagerRepairExecutor() {
	return useQuery({
		queryKey: ["manager_repairs_executor"],
		queryFn: getRepairs,
	});
}
