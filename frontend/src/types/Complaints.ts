export type ComplaintPost = {
	title: string;
	content: string;
	date: string;
};

export type Complaint = {
	title: string;
	content: string;
	date: string;
	idManager: number;
	idTenant: number;
	id_complaint: number;
};

export type ComplaintGet = {
	body: Complaint[];
};
