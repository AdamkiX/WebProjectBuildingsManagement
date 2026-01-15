"use client";
import { instanceConfig } from "@/interfaces/instanceConfig";
import InputWithLabel from "@/components/InputWithLabel";
import { getFormField } from "@/helpers/formHelpers";
import useCreateComplaint from "@/mutations/ComplaintMutation";
import { ComplaintPost } from "@/types/Complaints";
import useContractors from "@/queries/ContractorsQuery";
import useManagerRepair from "@/queries/ManagerRepairQuery";
import useCreateManagerRepair from "@/mutations/RepairManagerMutation";
import { RepairManagerPost } from "@/types/Repairs";

function ManagerAddRepairForm(): JSX.Element {
	const repairMutation = useCreateManagerRepair();

	const contractorsQuery = useContractors();
	const repairsQuery = useManagerRepair();

	const submitHandler = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		const data = new FormData(event.target as HTMLFormElement);
		try {
			const newRepair: RepairManagerPost = {
				repair: {
					cost: parseInt(getFormField(data, "cost")),
					startDate: getFormField(data, "startDate").split("T")[0],
					endDate: getFormField(data, "endDate").split("T")[0],
					plannedStartDate: getFormField(data, "startDate").split(
						"T"
					)[0],
					plannedEndDate: getFormField(data, "endDate").split("T")[0],
					idExecutor: parseInt(getFormField(data, "idExecutor")),
					idRepairCom: parseInt(getFormField(data, "idRepairCom")),
					status: "pending",
				},
				idRepairCom: parseInt(getFormField(data, "idRepairCom")),
			};
			repairMutation.mutate(newRepair);
		} catch (error) {
			alert("All fields must be filled!");
		}
	};
	if (contractorsQuery.isPending || repairsQuery.isPending)
		return <div>Loading...</div>;
	if (contractorsQuery.isError || repairsQuery.isError)
		return <div>Error</div>;
	return (
		<div>
			<h1 className="mb-4 text-2xl font-bold">Add Repair</h1>
			<form
				id="addForm"
				className="m-auto grid h-min w-min items-center justify-center gap-3 text-lg"
				onSubmit={submitHandler}
			>
				<div className="grid w-[30vw] grid-cols-[2fr_3fr] items-center gap-y-1">
					<label htmlFor="idRepairCom">Fault</label>
					<select
						id="idRepairCom"
						name="idRepairCom"
						className="rounded-xl px-4 py-2"
						disabled={repairsQuery.isPending}
					>
						{repairsQuery.data?.repairComs.map((repairCom) => {
							return (
								<option key={repairCom.id} value={repairCom.id}>
									{repairCom.title}
								</option>
							);
						})}
					</select>

					<label htmlFor="idExecutor">Contractor</label>
					<select
						id="idExecutor"
						name="idExecutor"
						className="rounded-xl px-4 py-2"
						disabled={contractorsQuery.isPending}
					>
						{contractorsQuery.data.map((contractor) => {
							return (
								<option
									key={contractor.nip}
									value={contractor.id_executor}
								>
									{contractor.company_name}
								</option>
							);
						})}
					</select>
					<InputWithLabel id="cost" label="Cost" type="number" />
					<InputWithLabel
						id="startDate"
						label="Start Date"
						type="date"
					/>
					<InputWithLabel id="endDate" label="End date" type="date" />
				</div>
				<input
					type="submit"
					value={`+ Add repair`}
					className="mt-2 w-min justify-self-center rounded-xl bg-main-green px-8 py-4 text-xl font-bold text-white"
				/>
			</form>
		</div>
	);
}

export const ManagerAddRepair: instanceConfig = {
	name: "repair",
	contentFunc: ManagerAddRepairForm,
};
