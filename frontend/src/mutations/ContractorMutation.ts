import axios from "@/providers/AxiosInstance";
import { Contractor, ContractorPostRequest } from "@/types/Contractors";
import { useMutation, useQueryClient } from "@tanstack/react-query";

async function createContractor(data: ContractorPostRequest) {
	await axios.post("/manager/executor", data);
}

export default function useCreateContractor(
	onErrorCallback?: (error: Error) => void
) {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: createContractor,

		onMutate: () => {},

		onError: (error) => {
			if (onErrorCallback !== undefined) onErrorCallback(error);
		},

		onSuccess: async () => {
			await queryClient.invalidateQueries({ queryKey: ["contractors"] });
		},

		onSettled: () => {},
	});
}
