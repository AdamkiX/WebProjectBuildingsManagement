import axios from "@/providers/AxiosInstance";
import { TenantPayment } from "@/types/Payments";
import { useMutation } from "@tanstack/react-query";

async function addPayment(data: TenantPayment) {
	await axios.post("/tenants/pay", data);
}

export default function useAddPayment(callbacks?: {
	onSuccessCallback?: () => void;
	onErrorCallback?: (error: Error) => void;
}) {
	return useMutation({
		mutationFn: addPayment,
		onSuccess: async () => {
			if (callbacks?.onSuccessCallback !== undefined)
				callbacks.onSuccessCallback();
		},
		onError: (error) => {
			console.error(error);
			if (callbacks?.onErrorCallback !== undefined)
				callbacks.onErrorCallback(error);
		},
	});
}
