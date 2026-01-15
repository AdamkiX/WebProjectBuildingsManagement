"use client";
import type { Building } from "@/types/building";
import { Column } from "@/types/Table";
import Topbar from "@/components/topbar/topbar";
import Table from "@/components/table/Table";
import { useBuildings } from "@/queries/BuildingsQuery";
import { useContext } from "react";
import {
	BuildingContext,
	BuildingContextProps,
} from "@/providers/BuildingProvider";

const columns: Column<Building & { onClick?: () => void }>[] = [
	{
		header: "Address",
		objectKey: "address",
		textAlign: "left",
	},
	{
		header: "City",
		objectKey: "city",
		textAlign: "right",
		divider: "right",
	},
	{
		header: "No. of Apartments",
		objectKey: "apartmentsCount",
		textAlign: "center",
		divider: "right",
	},
	{
		header: "Select building",
		text: "Select",
		textAlign: "center",
	},
];

export default function BuildingsPage() {
	const { setBuildingId } = useContext(
		BuildingContext
	) as BuildingContextProps;

	const query = useBuildings(setBuildingId);

	if (query.isError) {
		console.log(query.error);
	}

	// console.log(query.data[0].onClick());

	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar title="Buildings" addElement={false} filterConfig={{}} />
			{query.isPending && <div>Loading...</div>}
			{query.isError && <div>Error</div>}
			{query.isSuccess && <Table columns={columns} data={query.data} />}
		</div>
	);
}
