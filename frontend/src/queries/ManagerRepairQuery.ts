import axios from "@/providers/AxiosInstance";
import { Repair } from "@/types/Repairs";
import { ApartmentFromApi, ApartmentsGet } from "@/types/apartment";
import { useQuery } from "@tanstack/react-query";

async function getRepairs() {
	const response = await axios.get<{
		apartments: ApartmentFromApi[];
		repairComs: Repair[];
	}>("/repaircom");
	console.log(response.data);
	if (Object.keys(response.data).includes("repairComs")) return response.data;
	else return { apartments: [], repairComs: [] };
}

export default function useManagerRepair() {
	return useQuery({
		queryKey: ["manager_repairs"],
		queryFn: getRepairs,
	});
}
