import axios from "@/providers/AxiosInstance";
import { useQuery } from "@tanstack/react-query";
import { Tenant, TenantResponse } from "@/types/Tenants";
// : Promise<Tenant[]>
async function fetchTenants() {
	const request = await axios.get<{ tenants: Tenant[] }>("/managers/tenants");
	return request.data;
}

export default function useTenants() {
	return useQuery({
		queryKey: ["tenants"],
		queryFn: fetchTenants,
	});
}
