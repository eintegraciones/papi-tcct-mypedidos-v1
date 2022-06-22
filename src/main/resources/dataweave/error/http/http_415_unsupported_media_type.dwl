%dw 2.0
output application/json
---
{
	code: "415",
	message: "Unsupported Media Type",
	moreInformation: {
		description: error.description  as String default ""
	}
}