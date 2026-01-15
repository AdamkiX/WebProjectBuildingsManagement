import type { apartment } from "@/types/apartment";

export type Building = {
	id: number;
	address: string;
	city: string;
	province: string;
	apartments: any[];
	apartmentsCount: number;
};

export type BuildingResponseType = {
	address: string;
	apartmentNumber: number;
	apartments: any[];
	city: string;
	id_building: number;
	province: string;
};
