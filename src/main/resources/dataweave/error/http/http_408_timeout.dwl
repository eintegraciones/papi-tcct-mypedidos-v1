%dw 2.0
output application/json
---
{
	code: "408",
	message: "Request timeout",
	moreInformation: {
		description: error.description  as String default ""
	}
}