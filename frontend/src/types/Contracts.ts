import { ApartmentFromApi } from "./apartment";
import { Tenant } from "./Tenants";

export type Contract = {
	agrDate: string;
	id: number;
	idApartment: number;
	idManager: number;
	idTenant: number;
	rentEndDate: string;
	rentPrice: number;
	rentStartDate: string;
	status: "active";
};

export type ContractGet = {
	body: {
		rentAgreements: Contract[];
		apartments: ApartmentFromApi[];
		tenants: Tenant[];
	};
};

export type ContractPost = {
	agrDate: string;
	rentStartDate: string;
	rentEndDate: string;
	rentPrice: number;
	idTenant: number;
	idApartment: number;
	status: "active";
};
