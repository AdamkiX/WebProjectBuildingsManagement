import type { Column } from "@/types/Table";
import clsx from "clsx";

export default function TableHeader<Type>(props: {
	columnConfig: Column<Type>[];
}) {
	return (
		<>
			{props.columnConfig.map((column, i) => {
				if ("objectKey" in column) {
					return (
						<h3
							key={i}
							className={clsx({
								"font-bold": true,
								"text-left": column.textAlign === "left",
								"text-center": column.textAlign === "center",
								"text-right": column.textAlign === "right",
								"mr-1": column.divider === "right",
								"ml-1": column.divider === "left",
							})}
						>
							{column.header}
						</h3>
					);
				} else {
					return (
						<h3
							key={i}
							className={clsx({
								"text-center font-bold": true,
							})}
						>
							{column.header}
						</h3>
					);
				}
			})}
		</>
	);
}
