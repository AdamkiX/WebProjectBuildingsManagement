"use client";
import Table from "@/components/table/Table";
import Topbar from "@/components/topbar/topbar";
import { TenantAddComplaint } from "@/forms/TenantAddComplaint";
import useComplaints from "@/queries/ComplaintQuery";
import { Column } from "@/types/Table";
import { Complaint } from "@/types/Complaints";

const columns: Column<Complaint>[] = [
	{ header: "Title", objectKey: "title", textAlign: "left" },
	{ header: "Content", objectKey: "content", textAlign: "left" },
];

export default function Complaints() {
	const complaintsQuery = useComplaints();
	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar
				title="Complaints"
				filterConfig={{}}
				addElement={true}
				instanceConfig={TenantAddComplaint}
				displayApartment={true}
			/>
			{complaintsQuery.isPending && <div>Loading...</div>}
			{complaintsQuery.isError && <div>Error</div>}
			{complaintsQuery.isSuccess && (
				<Table data={complaintsQuery.data} columns={columns} />
			)}
		</div>
	);
}
