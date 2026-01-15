"use client";
import Table from "@/components/table/Table";
import Topbar from "@/components/topbar/topbar";
import { Contractor } from "@/types/Contractors";
import useContractors from "@/queries/ContractorsQuery";
import type { Column } from "@/types/Table";
import { AdminAddContractor } from "@/forms/AdminAddContractorForm";

const columns: Column<Contractor>[] = [
	{
		header: "Name",
		objectKey: "company_name",
		textAlign: "left",
	},
	{
		header: "Type",
		objectKey: "work_type",
		textAlign: "right",
	},
	{
		header: "Phone number",
		objectKey: "phone",
		textAlign: "center",
		divider: "left",
	},
];

export default function Contractors() {
	const contractorsQuery = useContractors();

	if (contractorsQuery.isError) {
		console.error(contractorsQuery.error);
	}

	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar
				title="Contractors"
				addElement={true}
				filterConfig={{}}
				instanceConfig={AdminAddContractor}
			/>
			{contractorsQuery.isPending && <div>Loading...</div>}
			{contractorsQuery.isError && <div>Error</div>}
			{contractorsQuery.isSuccess && (
				<Table data={contractorsQuery.data} columns={columns} />
			)}
		</div>
	);
}
