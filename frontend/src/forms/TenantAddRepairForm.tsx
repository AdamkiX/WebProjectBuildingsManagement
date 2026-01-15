"use client";
import { instanceConfig } from "@/interfaces/instanceConfig";
import InputWithLabel from "@/components/InputWithLabel";
import { getFormField } from "@/helpers/formHelpers";
import useCreateRepair from "@/mutations/RepairMutation";
import { RepairPost } from "@/types/Repairs";
import { useContext } from "react";
import {
	ApartmentContext,
	ApartmentContextProps,
} from "@/providers/ApartmentProvider";

function TenantAddRepairForm(): JSX.Element {
	const repairMutation = useCreateRepair({
		onSuccessCallback: () => {
			console.log("Repair added");
		},
		onErrorCallback: () => {},
	});
	const apartmentContext = useContext(
		ApartmentContext
	) as ApartmentContextProps;

	const submitHandler = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		const data = new FormData(event.target as HTMLFormElement);
		try {
			// todo get idApartment

			const newRepair: RepairPost = {
				title: getFormField(data, "title"),
				description: getFormField(data, "description"),
				creationDate: new Date().toISOString().split("T")[0],
				status: "active",
				idApartment: apartmentContext.apartmentId!,
			};
			repairMutation.mutate(newRepair);
		} catch (error) {
			alert("All fields must be filled!");
		}
	};

	return (
		<div>
			<h1 className="mb-4 text-2xl font-bold">Report fault</h1>
			<form
				id="addForm"
				className="m-auto grid h-min w-min items-center justify-center gap-3 text-lg"
				onSubmit={submitHandler}
			>
				<div className="grid w-[30vw] grid-cols-[2fr_3fr] items-center gap-y-1">
					<InputWithLabel
						id="title"
						label="Title"
						type="text"
						name="title"
						placeholder="Title"
					/>
				</div>
				<textarea
					id="description"
					name="description"
					placeholder="Description"
					className="h-[20vh] w-full resize-none rounded-lg border-2 border-[#D3DAE9] px-2 py-1 text-base placeholder-main-orange/40"
				/>
				<input
					type="submit"
					value={`+ Report fault`}
					className="mt-2 w-min justify-self-center rounded-xl bg-main-green px-8 py-4 text-xl font-bold text-white"
				/>
			</form>
		</div>
	);
}

export const TenantAddRepair: instanceConfig = {
	name: "fault",
	contentFunc: TenantAddRepairForm,
};
