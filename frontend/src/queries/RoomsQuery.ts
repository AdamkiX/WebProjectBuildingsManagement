import axios from "@/providers/AxiosInstance";
import { useQuery } from "@tanstack/react-query";
import { RoomType } from "@/types/Rooms";

async function fetchRoomTypes() {
	const request = await axios.get<{ roomTypes: RoomType[] }>(
		"/managers/get_room_types"
	);
	return request.data.roomTypes;
}

export default function useRoomTypes() {
	return useQuery({
		queryKey: ["room_types"],
		queryFn: fetchRoomTypes,
	});
}
