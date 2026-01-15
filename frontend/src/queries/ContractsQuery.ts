import axios from "@/providers/AxiosInstance";
import { useQuery } from "@tanstack/react-query";
import { ContractGet } from "@/types/Contracts";

async function fetchContracts() {
	const request = await axios.get<ContractGet>("/agreements");
	// console.log(request.data);
	return request.data.body;
}

export default function useContractsQuery() {
	return useQuery({
		queryKey: ["contracts"],
		queryFn: fetchContracts,
	});
}
