import axios from "@/providers/AxiosInstance";
import { useQuery } from "@tanstack/react-query";
import { ApartmentsGet } from "@/types/apartment";

async function fetchTenantsApartments() {
	const response = await axios.get<{ trapartments: ApartmentsGet[] }>(
		"/tenants/all_apartments"
	);
	// console.log(response.data);
	return response.data.trapartments;
}

export default function useTenantsApartments() {
	return useQuery({
		queryKey: ["tenants_apartments"],
		queryFn: fetchTenantsApartments,
	});
}
