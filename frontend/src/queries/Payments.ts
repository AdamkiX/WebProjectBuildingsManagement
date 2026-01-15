import axios from "@/providers/AxiosInstance";
import { TenantPaymentsGet } from "@/types/Payments";
import { useQuery } from "@tanstack/react-query";

async function getPayments() {
	const response = await axios.get<{ rpayments: TenantPaymentsGet[] }>(
		"/tenants/all_payments"
	);
	return response.data.rpayments;
}

export default function usePayments() {
	return useQuery({ queryKey: ["payments"], queryFn: getPayments });
}
