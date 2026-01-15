export type Column<Type> =
	| {
			header: string;
			objectKey: keyof Type;
			divider?: "left" | "right";
			textAlign: "left" | "center" | "right";
	  }
	| Button;

type Button = {
	header: string;
	text: string;
};

export type TableProps<Type> = {
	data: Type[];
	columns: Column<Type>[];
};
