export type ContractorResponse = {
	executor: {
		id_executor: number;
		name: string;
		surname: string;
		phone: string;
		birthdate: Date;
		company_name: string;
		address: string;
		nip: string;
		regon: string;
	};
	workType: {
		work_type: string;
	};
};

export type ContractorPostRequest = {
	executor: {
		name: string;
		surname: string;
		phone: string;
		birthdate: string;
		company_name: string;
		address: string;
		nip: string;
		regon: string;
	};
	workType: {
		work_type: string;
	};
};

export type Contractor = {
	id_executor: number;
	name: string;
	surname: string;
	phone: string;
	birthdate: Date;
	company_name: string;
	address: string;
	nip: string;
	regon: string;
	work_type: string;
};
