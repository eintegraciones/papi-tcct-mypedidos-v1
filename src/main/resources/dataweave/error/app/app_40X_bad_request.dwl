%dw 2.0
output application/json
---
{
	code: vars.errorType.code,
	message: "Bad request",
	moreInformation: {
		description: vars.errorType.message
	}
}