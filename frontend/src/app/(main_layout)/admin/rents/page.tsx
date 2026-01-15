"use client";
import Table from "@/components/table/Table";
import Topbar from "@/components/topbar/topbar";
import useRentsManager from "@/queries/ManagerRentsQuery";
import { Rent } from "@/types/Rents";
import { Column } from "@/types/Table";

export default function Rents() {
	const rentsQuery = useRentsManager();
	// TODO join with apartments
	const columns: Column<Rent>[] = [
		{ header: "Apartment ID", objectKey: "idApartment", textAlign: "left" },
		{ header: "Start date", objectKey: "startDate", textAlign: "left" },
		{ header: "End date", objectKey: "endDate", textAlign: "left" },
		{ header: "Total amount", objectKey: "amount", textAlign: "left" },
		{ header: "Fully paid", objectKey: "payed", textAlign: "left" },
	];

	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar
				title="Rents"
				addElement={false}
				filterConfig={{}}
				// instanceConfig={ManagerAddRepair}
			/>
			{rentsQuery.isSuccess && (
				<Table columns={columns} data={rentsQuery.data.rents} />
			)}
		</div>
	);
}
