import axios from "@/providers/AxiosInstance";
import { RepairManagerPost } from "@/types/Repairs";
import { useMutation } from "@tanstack/react-query";

async function addManagerRepair(data: RepairManagerPost) {
	await axios.post("/managers/repair", data);
}

export default function useCreateManagerRepair(callbacks?: {
	onSuccessCallback?: () => void;
	onErrorCallback?: (error: Error) => void;
}) {
	return useMutation({
		mutationFn: addManagerRepair,
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
