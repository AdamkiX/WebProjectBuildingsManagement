"use client";
import { instanceConfig } from "@/interfaces/instanceConfig";
import InputWithLabel from "@/components/InputWithLabel";
import { getFormField } from "@/helpers/formHelpers";
import useCreateComplaint from "@/mutations/ComplaintMutation";
import { ComplaintPost } from "@/types/Complaints";
import usePayments from "@/queries/Payments";
import useAddPayment from "@/mutations/PaymentsMutation";
import { TenantPayment } from "@/types/Payments";

function TenantAddPaymentForm(): JSX.Element {
	const complaintMutation = useCreateComplaint();
	const paymentMutation = useAddPayment();

	const paymentsQuery = usePayments();

	const submitHandler = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		const data = new FormData(event.target as HTMLFormElement);
		try {
			const newPayment: TenantPayment = {
				amount: parseInt(getFormField(data, "amount")),
				idRent: parseInt(getFormField(data, "idRent")),
			};
			paymentMutation.mutate(newPayment);
		} catch (error) {
			alert("All fields must be filled!");
		}
	};

	return (
		<div>
			<h1 className="mb-4 text-2xl font-bold">Add Payment</h1>
			<form
				id="addForm"
				className="m-auto grid h-min w-min items-center justify-center gap-3 text-lg"
				onSubmit={submitHandler}
			>
				<div className="grid w-[30vw] grid-cols-[2fr_3fr] items-center gap-y-1">
					<InputWithLabel
						id="amount"
						label="Amount"
						type="number"
						name="amount"
					/>
				</div>
				<select
					id="idRent"
					name="idRent"
					className="rounded-xl px-4 py-2"
					disabled={paymentsQuery.isPending}
				>
					{paymentsQuery.data?.map((payment) => {
						return (
							<option
								key={payment.rent.id}
								value={payment.rent.id}
							>
								{payment.rent.startDate} -{" "}
								{payment.rent.startDate} ({payment.rent.amount})
							</option>
						);
					})}
				</select>
				<input
					type="submit"
					value={`+ Add payment`}
					className="mt-2 w-min justify-self-center rounded-xl bg-main-green px-8 py-4 text-xl font-bold text-white"
				/>
			</form>
		</div>
	);
}

export const TenantAddPayment: instanceConfig = {
	name: "payment",
	contentFunc: TenantAddPaymentForm,
};
