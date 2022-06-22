%dw 2.0
output application/json
---
{
	code: "403",
	message: "Forbidden",
	moreInformation: {
		description: error.description  as String default ""
	}
}