"use client";
import Table from "@/components/table/Table";
import Topbar from "@/components/topbar/topbar";
import { ManagerAddRepair } from "@/forms/AdminAddRepairForm";
import useManagerRepairExecutor from "@/queries/ManagerRepairExecutorQuery";
import { RepiarManagerExecutorGet } from "@/types/Repairs";
import { Column } from "@/types/Table";

export default function Repairs() {
	const repairsQuery = useManagerRepairExecutor();

	const columns: Column<RepiarManagerExecutorGet>[] = [
		{ header: "Executor ID", objectKey: "idExecutor", textAlign: "left" },
		{
			header: "Repair comission ID",
			objectKey: "idRepairCom",
			textAlign: "left",
		},
		{ header: "Start date", objectKey: "startDate", textAlign: "left" },
		{ header: "End date", objectKey: "endDate", textAlign: "left" },

		{ header: "Cost", objectKey: "cost", textAlign: "left" },
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

			{repairsQuery.isSuccess && (
				<Table columns={columns} data={repairsQuery.data.repairs} />
			)}
		</div>
	);
}
