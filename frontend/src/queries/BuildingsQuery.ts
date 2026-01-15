import axiosInstance from "@/providers/AxiosInstance";
import { Building, BuildingResponseType } from "@/types/building";
import { useQuery, useQueryClient, QueryClient } from "@tanstack/react-query";

async function fetchBuildings(
	setBuildingId: (buildingId: number) => void
): Promise<(Building & { onClick: () => void })[]> {
	const request = await axiosInstance.get<{
		buildings: BuildingResponseType[];
	}>("/managers/buildings");

	return request.data.buildings.map((building) => {
		return {
			id: building.id_building,
			address: building.address,
			city: building.city,
			province: building.province,
			apartments: building.apartments,
			apartmentsCount: building.apartmentNumber,
			onClick: () => {
				setBuildingId(building.id_building);
			},
		};
	});
}

async function fetchBuilding(buildingId: number | null) {
	console.log(`Fetched building number ${buildingId}`);
	const request = await axiosInstance.get<{ building: Building }>(
		`/managers/buildings/${buildingId}`
	);
	return request.data.building;
}

export function useBuildings(setBuildingId: (buildingId: number) => void) {
	return useQuery({
		queryKey: ["buildings"],
		queryFn: () => fetchBuildings(setBuildingId),
	});
}

export function useBuilding(buildingId: number | null) {
	return useQuery({
		queryKey: ["building"],
		queryFn: () => fetchBuilding(buildingId),
		enabled: buildingId !== null,
	});
}
