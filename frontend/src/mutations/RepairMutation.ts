import axios from "@/providers/AxiosInstance";
import { RepairPost } from "@/types/Repairs";
import { useMutation } from "@tanstack/react-query";

async function createRepair(data: RepairPost) {
	await axios.post("/repaircom", data);
	// return response.data;
}

export default function useCreateRepair(callbacks?: {
	onSuccessCallback?: () => void;
	onErrorCallback?: (error: Error) => void;
}) {
	return useMutation({
		mutationFn: createRepair,
		onSuccess: async () => {
			// await queryClient.invalidateQueries({ queryKey: ["contracts"] });
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
