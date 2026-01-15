import axiosInstance from "@/providers/AxiosInstance";
import { useQuery } from "@tanstack/react-query";
import { ApartmentsGet } from "@/types/apartment";
import {
	BuildingContext,
	BuildingContextProps,
} from "@/providers/BuildingProvider";
import { useContext } from "react";

async function fetchApartments(buildingId: number | null) {
	const request = await axiosInstance.get<{ trapartments: ApartmentsGet[] }>(
		`/rooms/${buildingId}`
	);
	// console.log(request.data);

	return request.data;
}

export default function useApartments() {
	const { buildingId } = useContext(BuildingContext) as BuildingContextProps;
	return useQuery({
		queryKey: ["apartments"],
		queryFn: async () => await fetchApartments(buildingId),
		enabled: buildingId !== null,
	});
}
