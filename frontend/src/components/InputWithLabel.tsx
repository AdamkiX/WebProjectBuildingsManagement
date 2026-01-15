type InputWithLabelProsp = {
	id: string;
	label: string;
	type: string;
	name?: string;
	placeholder?: string;
};

export default function InputWithLabel(props: InputWithLabelProsp) {
	return (
		<>
			<label className="block h-fit items-center" htmlFor={props.id}>
				{props.label}
			</label>
			<input
				type={props.type}
				name={props.name ?? props.id}
				id={props.id}
				placeholder={props.placeholder ?? props.label}
				className="block rounded-xl border-2 border-[#D3DAE9] px-4 py-2 text-base placeholder-main-orange/40"
			/>
		</>
	);
}
