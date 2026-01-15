// "use state";
import { instanceConfig } from "@/interfaces/instanceConfig";
import InputWithLabel from "@/components/InputWithLabel";
import useApartments from "@/queries/ApartmentsQuery";
import useTenants from "@/queries/TenantsQuery";
import useCreateContract from "@/mutations/ContractMutation";
import { ContractPost } from "@/types/Contracts";
import { getFormField } from "@/helpers/formHelpers";
import { Tenant } from "@/types/Tenants";

function AdminAddContractForm() {
	const apartmentsQuery = useApartments();
	const tenantsQuery = useTenants();
	const contractsMutation = useCreateContract({
		onSuccessCallback: () => alert("Contract added"),
	});

	const onSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		try {
			const data = new FormData(event.target as HTMLFormElement);
			const newContract: ContractPost = {
				agrDate: new Date().toISOString().split("T")[0],
				rentStartDate: getFormField(data, "rentStartDate"),
				rentEndDate: getFormField(data, "rentEndDate"),
				rentPrice: parseInt(getFormField(data, "rentPrice")),
				idTenant: parseInt(getFormField(data, "idTenant")),
				idApartment: parseInt(getFormField(data, "apartmentId")),
				status: "active",
			};

			contractsMutation.mutate(newContract);
		} catch (error) {
			alert(error);
		}
	};

	return (
		<>
			<h1 className="mb-4 text-2xl font-bold">Add Contract</h1>
			<form
				id="addForm"
				className="m-auto grid h-min w-min items-center justify-center gap-3 text-lg"
				onSubmit={onSubmit}
			>
				<div className="grid w-[30vw] grid-cols-[2fr_3fr] items-center gap-y-1">
					<label htmlFor="apartmentId">Apartment</label>
					<select
						id="apartmentId"
						name="apartmentId"
						className="rounded-xl px-4 py-2"
						disabled={apartmentsQuery.isPending}
					>
						{apartmentsQuery.data?.trapartments.map((apartment) => {
							if (apartment.apartment.idTenant === null) {
								return (
									<option
										key={apartment.apartment.id}
										value={apartment.apartment.id}
									>
										{apartment.apartment.apt_number}
									</option>
								);
							}
						})}
					</select>
					{/* TODO change to <seletc> when GET /managers/tenats is implemented*/}
					<label htmlFor="idTenant">Tenant</label>
					<select
						id="idTenant"
						name="idTenant"
						className="rounded-xl px-4 py-2"
						disabled={tenantsQuery.isPending}
					>
						{tenantsQuery.data?.tenants.map((tenant) => {
							return (
								<option key={tenant.id} value={tenant.id}>
									{tenant.name} {tenant.surname}
								</option>
							);
						})}
					</select>

					<InputWithLabel
						id="rentPrice"
						label="Price"
						type="number"
					/>
					<InputWithLabel
						id="rentStartDate"
						label="Rent start date"
						type="date"
					/>
					<InputWithLabel
						id="rentEndDate"
						label="Rent end date"
						type="date"
					/>
				</div>
				<input
					type="submit"
					value={`+ Add Contract`}
					className="mt-2 w-min justify-self-center rounded-xl bg-main-green px-8 py-4 text-xl font-bold text-white"
				/>
			</form>
		</>
	);
}

export const AdminAddContract: instanceConfig = {
	name: "contract",
	contentFunc: AdminAddContractForm,
};
