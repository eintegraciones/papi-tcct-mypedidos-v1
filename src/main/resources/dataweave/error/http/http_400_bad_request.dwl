%dw 2.0
output application/json
---
{
	code: "400",
	message: "Bad request",
	moreInformation: {
		description: error.description  as String default ""
	}
}