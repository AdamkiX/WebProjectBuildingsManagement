import { RoomPost as Room } from "./Rooms";
import { Tenant } from "./Tenants";

export type apartment = {
	id: number;
	name: string;
	rooms: { room: string; count: number }[];
	area: number;
	floor: number;
	owner: string | null;
	address: string;
	number: string;
	details: string;
};

export type ApartmentFromApi = {
	apt_area: number;
	apt_number: number;
	details: string;
	id: number;
	idManager: number;
	idTenant: number | null;
	id_building: number;
	level: number;
};

export type ApartmentPost = {
	apt_area: string;
	apt_number: string;
	details: string;
	id_building: string;
	level: string;
	// TODO rooms
};

export type ApartmentsGet = {
	apartment: ApartmentFromApi;
	tenant: Tenant;
	rooms: Room[];
};
