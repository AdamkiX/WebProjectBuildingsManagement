"use client";
import Table from "@/components/table/Table";
import Topbar from "@/components/topbar/topbar";
import useManagerComplaints from "@/queries/ManagerComplaintQuery";
import useTenants from "@/queries/TenantsQuery";

export default function Complaints() {
	const complaintsQuery = useManagerComplaints();
	const tenantsQuery = useTenants();

	const complaintsTenantsJoin = () => {
		const complaints = complaintsQuery.data;
		const tenants = tenantsQuery.data;
		if (!complaints || !tenants) return [];
		return complaints.map((complaint) => {
			const tenant = tenants.tenants.find(
				(tenant) => tenant.id === complaint.idTenant
			);
			return {
				...complaint,
				tenant: `${tenant?.name} ${tenant?.surname}`,
			};
		});
	};

	const joinedData = complaintsTenantsJoin();

	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar title="Complaints" filterConfig={{}} addElement={false} />
			{complaintsQuery.isPending && tenantsQuery.isPending && (
				<div>Loading...</div>
			)}
			{(complaintsQuery.isError || tenantsQuery.isError) && (
				<div>Error</div>
			)}
			{complaintsQuery.isSuccess && tenantsQuery.isSuccess && (
				<Table
					data={joinedData}
					columns={[
						{
							header: "Date",
							objectKey: "date",
							textAlign: "left",
						},
						{
							header: "Tenant",
							objectKey: "tenant",
							textAlign: "center",
						},
						{
							header: "Title",
							objectKey: "title",
							textAlign: "left",
							divider: "left",
						},
						{
							header: "Content",
							objectKey: "content",
							textAlign: "left",
						},
					]}
				/>
			)}
		</div>
	);
}
