"use client";
import Table from "@/components/table/Table";
import Topbar from "@/components/topbar/topbar";
import { ManagerAddRepair } from "@/forms/AdminAddRepairForm";
import useManagerRepair from "@/queries/ManagerRepairQuery";
import { Repair } from "@/types/Repairs";
import { Column } from "@/types/Table";

export default function Repairs() {
	const repairMutation = useManagerRepair();

	const columns: Column<Repair>[] = [
		{ header: "Apartment ID", objectKey: "idApartment", textAlign: "left" },
		{ header: "Title", objectKey: "title", textAlign: "left" },
		{ header: "Description", objectKey: "description", textAlign: "left" },
		{
			header: "Report date",
			objectKey: "creationDate",
			textAlign: "center",
		},
		{ header: "Status", objectKey: "status", textAlign: "center" },
	];

	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar
				title="Repairs"
				addElement={true}
				filterConfig={{}}
				instanceConfig={ManagerAddRepair}
			/>

			{repairMutation.isSuccess && (
				<Table
					columns={columns}
					data={repairMutation.data?.repairComs}
				/>
			)}
		</div>
	);
}
