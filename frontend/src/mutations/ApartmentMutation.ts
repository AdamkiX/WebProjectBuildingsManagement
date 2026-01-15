import axios from "@/providers/AxiosInstance";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { ApartmentPost } from "@/types/apartment";
import { RoomPost } from "@/types/Rooms";

async function createApartment(data: {
	apartment: ApartmentPost;
	rooms: RoomPost[];
}) {
	await axios.post("/managers/apartments_and_rooms", data);
}

export default function useCreateApartment(
	onErrorCallback?: (error: Error) => void
) {
	const queryClient = useQueryClient();

	return useMutation({
		mutationFn: createApartment,
		onSuccess: async () => {
			await queryClient.invalidateQueries({ queryKey: ["apartments"] });
		},
		onError: (error) => {
			if (onErrorCallback !== undefined) onErrorCallback(error);
		},
	});
}
