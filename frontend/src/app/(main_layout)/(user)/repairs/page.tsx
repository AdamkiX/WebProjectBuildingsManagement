"use client";
import Topbar from "@/components/topbar/topbar";
import { TenantAddRepair } from "@/forms/TenantAddRepairForm";
import useRepairs from "@/queries/RepairQuery";

export default function Page() {
	const repairsQuery = useRepairs();
	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			{/* TODO form for adding repairs */}
			<Topbar
				title="Repairs"
				addElement={true}
				instanceConfig={TenantAddRepair}
				filterConfig={{}}
				displayApartment={true}
			/>
		</div>
	);
}
