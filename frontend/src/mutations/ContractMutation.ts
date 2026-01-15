import axios from "@/providers/AxiosInstance";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { Contract, ContractPost } from "@/types/Contracts";

async function createContract(data: ContractPost) {
	await axios.post("/managers/agreement", data);
}
export default function useCreateContract(callbacks?: {
	onSuccessCallback?: () => void;
	onErrorCallback?: (error: Error) => void;
}) {
	const queryClient = useQueryClient();

	return useMutation({
		mutationFn: createContract,
		onSuccess: async () => {
			await queryClient.invalidateQueries({ queryKey: ["contracts"] });
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
