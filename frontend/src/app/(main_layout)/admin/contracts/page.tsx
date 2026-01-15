"use client";
import Table from "@/components/table/Table";
import Topbar from "@/components/topbar/topbar";
import { AdminAddContract } from "@/forms/AdminAddContractForm";
import useContractsQuery from "@/queries/ContractsQuery";
import { Contract } from "@/types/Contracts";
import { Column } from "@/types/Table";

const columns: Column<Contract>[] = [
	{
		// TODO zmienić na imie i nazwisko usera
		header: "Tenant",
		objectKey: "idTenant",
		textAlign: "left",
	},
	{
		// TODO zmienić na adres (adres budynku + numer mieszkania)
		header: "Apartment",
		objectKey: "idApartment",
		textAlign: "right",
	},
	{
		header: "Rent start date",
		objectKey: "rentStartDate",
		textAlign: "center",
		divider: "left",
	},
	{
		header: "Rent end date",
		objectKey: "rentEndDate",
		textAlign: "center",
	},
	{
		header: "Price",
		objectKey: "rentPrice",
		textAlign: "center",
		divider: "left",
	},
];

export default function Contractrs() {
	const contractsQuery = useContractsQuery();
	return (
		<div className="m-5 mt-7 grid grid-rows-[1fr_auto] gap-14">
			<Topbar
				title="Contracts"
				addElement={true}
				filterConfig={{}}
				instanceConfig={AdminAddContract}
			/>
			{contractsQuery.isSuccess && (
				<Table
					data={contractsQuery.data.rentAgreements}
					columns={columns}
				/>
			)}
		</div>
	);
}
