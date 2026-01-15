"use client";
import Table from "@/components/table/Table";
import Topbar from "@/components/topbar/topbar";
import { TenantAddPayment } from "@/forms/TenantAddPaymentForm";
import usePayments from "@/queries/Payments";
import { Rent } from "@/types/Rents";
import { Column } from "@/types/Table";

export default function Payments() {
	const paymentsQuery = usePayments();

	const rentsOnly = paymentsQuery.data?.map((payment) => {
		const sum = payment.payments.reduce(
			(acc, curr) => acc + curr.amount,
			0
		);
		return { ...payment.rent, sum };
	}) as (Rent & { sum: number })[];

	const columns: Column<Rent & { sum: number }>[] = [
		{ header: "Apartment ID", objectKey: "idApartment", textAlign: "left" },
		{ header: "Start date", objectKey: "startDate", textAlign: "left" },
		{ header: "End date", objectKey: "endDate", textAlign: "left" },
		{ header: "Paid amount", objectKey: "sum", textAlign: "left" },
		{ header: "Total amount", objectKey: "amount", textAlign: "left" },
	];

	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar
				title="Payments"
				addElement={true}
				filterConfig={{}}
				displayApartment={true}
				instanceConfig={TenantAddPayment}
			/>
			{paymentsQuery.isSuccess && rentsOnly !== undefined && (
				<Table data={rentsOnly} columns={columns} />
			)}
		</div>
	);
}
