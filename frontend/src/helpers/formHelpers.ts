export function getFormField(formData: FormData, key: string): string {
	const value = formData.get(key);
	if (value === null) throw new Error(`Field ${key} is empty`);
	if (typeof value !== "string") throw new Error("Not implemented");
	return value;
}
