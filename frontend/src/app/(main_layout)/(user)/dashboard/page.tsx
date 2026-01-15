"use client";
import useTenantsApartments from "@/queries/TenantsApartmentsQuery";
import Topbar from "@/components/topbar/topbar";
import { Column } from "@/types/Table";
import { ApartmentFromApi } from "@/types/apartment";
import Table from "@/components/table/Table";
import { useContext } from "react";
import {
	ApartmentContext,
	ApartmentContextProps,
} from "@/providers/ApartmentProvider";

const columns: Column<ApartmentFromApi & { onClick?: () => void }>[] = [
	{ header: "Apartment number", objectKey: "apt_number", textAlign: "left" },
	{ header: "Building ID", objectKey: "id_building", textAlign: "left" },
	{
		header: "Select apartment",
		text: "Select",
		textAlign: "center",
	},
];

export default function Dashboard() {
	const tenantApartmentsQuery = useTenantsApartments();
	const { setApartmentId } = useContext(
		ApartmentContext
	) as ApartmentContextProps;

	const apartments = tenantApartmentsQuery.data?.map((trapartment) => {
		return {
			...trapartment.apartment,
			onClick: () => {
				setApartmentId(trapartment.apartment.id);
			},
		};
	});

	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar
				title="Dashboard"
				addElement={false}
				filterConfig={{}}
				displayApartment={true}
			/>
			{tenantApartmentsQuery.isPending && <div>Loading...</div>}
			{tenantApartmentsQuery.isError && <div>Error</div>}
			{tenantApartmentsQuery.isSuccess && apartments !== undefined && (
				<Table columns={columns} data={apartments} />
			)}
		</div>
	);
}
