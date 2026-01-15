import { SetStateAction } from "react";

export const LoginInputField = ({
	label,
	type,
	value,
	onChange,
	name,
	error = false,
	lock = false,
}: {
	label: string;
	type: string;
	value: string;
	onChange: (value: SetStateAction<string>) => void;
	name: string;
	error?: boolean;
	lock?: boolean;
}) => {
	return (
		<div className="mt-4 flex flex-col">
			<label
				className="px-1 text-sm text-gray-600 dark:text-white/80"
				htmlFor={label}
			>
				{label}
			</label>
			<input
				className={`text-md block w-full rounded-lg border-2 border-gray-300 bg-white px-5 py-2 placeholder-gray-600 shadow-md focus:border-gray-600 focus:bg-white focus:placeholder-gray-500 focus:outline-none dark:border-gray-500 dark:bg-gray-700 dark:text-white dark:placeholder-gray-500 dark:focus:border-gray-500 dark:focus:bg-gray-700 dark:focus:placeholder-gray-400 dark:focus:outline-none ${
					error ? "border-red-500" : ""
				}`}
				type={type}
				value={value}
				onChange={(e) => onChange(e.target.value)}
				id={name}
				name={name}
				readOnly={lock}
			/>
		</div>
	);
};
