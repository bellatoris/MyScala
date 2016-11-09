# Recap: Functions and Pattern Matching

## Recap: Case Classes
* Case classes are Scala's preferred way to define complex data.
* Example : Representing `JSON` (Java Script Object Notation)

```json
{ "firstName" : "John",
  "lastName" : "Smith",
  "assress": {
  	"stressAddress" : "21 2nd Street",
  	"state" : "NY",
  	"postalCode" : 10021
  },
  "phoneNumbers" : [
  	{ "type" : "home", "number" : "212 555-12345" },	