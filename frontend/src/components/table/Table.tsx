"use client";
import type { TableProps } from "@/types/Table";
import TableHeader from "@/components/table/TableHeader";
import TableRow from "@/components/table/TableRow";

export default function Table<Type>(
	props: TableProps<Type & { onClick?: () => void }>
) {
	const numberOfCols = props.columns.length;
	return (
		<div className={`inline-grid grid-cols-${numberOfCols} gap-y-2`}>
			<TableHeader columnConfig={props.columns} />
			{props.data.map((value, i) => {
				return (
					<TableRow
						key={i}
						columnConfig={props.columns}
						value={value}
					/>
				);
			})}
		</div>
	);
}
