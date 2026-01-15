import { ComplaintPost } from "@/types/Complaints";
import axios from "@/providers/AxiosInstance";
import { useMutation, useQueryClient } from "@tanstack/react-query";

async function createComplaint(complaint: ComplaintPost) {
	return await axios.post("/tenant/complaint", complaint);
}

export default function useCreateComplaint(callbacks?: {
	onSuccessCallback?: () => void;
	onErrorCallback?: (error: Error) => void;
}) {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: createComplaint,
		onSuccess: async () => {
			queryClient.invalidateQueries({ queryKey: ["complaints"] });
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
