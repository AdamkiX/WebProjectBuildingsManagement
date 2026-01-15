"use client";
import React from "react";
import InputWithLabel from "@/components/InputWithLabel";
import type { instanceConfig } from "@/interfaces/instanceConfig";
import { Contractor, ContractorPostRequest } from "@/types/Contractors";
import useCreateContractor from "@/mutations/ContractorMutation";
import { getFormField } from "@/helpers/formHelpers";

function FormComponent(): JSX.Element {
	const createContractorMutation = useCreateContractor((error) => {
		console.log(error);
	});

	const onSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		try {
			const data = new FormData(event.target as HTMLFormElement);

			let emptyField = false;

			data.forEach((val, key) => {
				if (val === "") {
					emptyField = true;
				}
			});

			if (emptyField) {
				alert("All fields must be filled!");
				return;
			}

			const newContractor: ContractorPostRequest = {
				executor: {
					name: getFormField(data, "name"),
					surname: getFormField(data, "surname"),
					phone: getFormField(data, "phone"),
					birthdate: getFormField(data, "birthdate"),
					company_name: getFormField(data, "company_name"),
					address: getFormField(data, "address"),
					nip: getFormField(data, "nip"),
					regon: getFormField(data, "regon"),
				},
				workType: { work_type: getFormField(data, "work_type") },
			};
			createContractorMutation.mutate(newContractor);
		} catch (error) {
			alert(error);
		}
	};

	return (
		<>
			<h1 className="mb-4 text-2xl font-bold">Add Contractor</h1>
			<form
				id="addForm"
				className="m-auto grid h-min w-min items-center justify-center gap-3 text-lg"
				onSubmit={onSubmit}
			>
				<div className="grid w-[30vw] grid-cols-[2fr_3fr] items-center gap-y-1">
					<InputWithLabel id="name" label="Name" type="text" />
					<InputWithLabel id="surname" label="Surname" type="text" />
					<InputWithLabel
						id="phone"
						label="Phone number"
						type="text"
					/>
					<InputWithLabel
						id="birthdate"
						label="Birthdate"
						type="date"
					/>
					<InputWithLabel
						id="company_name"
						label="Company Name"
						type="text"
					/>
					<InputWithLabel id="address" label="Address" type="text" />
					<InputWithLabel id="nip" label="NIP" type="text" />
					<InputWithLabel id="regon" label="REGON" type="text" />
					<InputWithLabel
						id="work_type"
						label="Work type"
						type="text"
					/>
				</div>
				<input
					type="submit"
					value={`+ Add Contractor`}
					className="mt-2 w-min justify-self-center rounded-xl bg-main-green px-8 py-4 text-xl font-bold text-white"
				/>
			</form>
		</>
	);
}

export const AdminAddContractor: instanceConfig = {
	name: "contractor",
	contentFunc: FormComponent,
};
