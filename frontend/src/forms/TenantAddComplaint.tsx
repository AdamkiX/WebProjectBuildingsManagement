"use client";
import { instanceConfig } from "@/interfaces/instanceConfig";
import InputWithLabel from "@/components/InputWithLabel";
import { getFormField } from "@/helpers/formHelpers";
import useCreateComplaint from "@/mutations/ComplaintMutation";
import { ComplaintPost } from "@/types/Complaints";

function TenantAddComplaintForm(): JSX.Element {
	const complaintMutation = useCreateComplaint();

	const submitHandler = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		const data = new FormData(event.target as HTMLFormElement);
		try {
			const newComplaint: ComplaintPost = {
				title: getFormField(data, "title"),
				content: getFormField(data, "description"),
				date: new Date().toISOString().split("T")[0],
			};
			complaintMutation.mutate(newComplaint);
		} catch (error) {
			alert("All fields must be filled!");
		}
	};

	return (
		<div>
			<h1 className="mb-4 text-2xl font-bold">Add Complaint</h1>
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
					value={`+ Add Complaint`}
					className="mt-2 w-min justify-self-center rounded-xl bg-main-green px-8 py-4 text-xl font-bold text-white"
				/>
			</form>
		</div>
	);
}

export const TenantAddComplaint: instanceConfig = {
	name: "complaint",
	contentFunc: TenantAddComplaintForm,
};
