export type RepairPost = {
	title: string;
	description: string;
	creationDate: string;
	status: "active";
	idApartment: number;
	// idRentAgreement: number;
};

export type Repair = {
	creationDate: string;
	description: string;
	id: number;
	idApartment: number;
	status: string;
	title: string;
};

export type RepairManagerPost = {
	repair: {
		cost: number;
		startDate: string;
		endDate: string;
		plannedStartDate: string;
		plannedEndDate: string;
		idExecutor: number;
		idRepairCom: number;
		status: "pending";
	};
	idRepairCom: number;
};

export type RepiarManagerExecutorGet = {
	cost: number;
	endDate: string;
	id: number;
	idExecutor: number;
	idManager: number;
	idRepairCom: number;
	plannedEndDate: string;
	plannedStartDate: string;
	startDate: string;
	status: string;
};
