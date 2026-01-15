import { Rent } from "./Rents";

export type Payment = {
	id_payment: number;
	payment_date: string;
	amount: number;
	idRent: number;
};

export type TenantPaymentsGet = {
	rent: Rent;
	payments: Payment[];
};

export type TenantPayment = {
	amount: number;
	idRent: number;
};
