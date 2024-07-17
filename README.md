# Spring Invoice
### Description

This application helps to manage invoices issued by owner and keeping track of payments.
In future there will also be way to keep track for more than single owner, customers being able to add payment and owner just confirming it and more things I'll come up by using it.

### Technology used:

- Backend
  - Java
  - Spring boot
    - Data JDBC
    - TBD Spring Security
  - Postgres
  - Docker
- Frontend
  - TBD (Next.js/React most likely with Tailwind/MaterialUI)

### Database
Database schema is inside resource directory. It has only four tables, but it will include few more for future (user, credentials, etc.). Right now it is utilizing UUIDs as primary keys as they are random at the cost of performance speed. To make it bit faster I added two indexes for tables Item and Payment to help a little with speed.

### Routes
## GET `api/customers`
#### Query Params
- sort
- size
- page

## POST `api/customers`
#### Body
```json
{
  "name": "Schoen, Roob and Deckow",
  "streetName": "Keon Plains 687",
  "city": "Lindgrenmouth",
  "zipCode": "12345",
  "ICO": 123456789
}
```
## GET `api/customers/:id`
#### Path Variables
- id

## GET `api/invoices`
#### Query Params
- sort
- size
- page
## POST `api/invoices`
#### Body
```json
{
    "invoiceNumber": "052024",
    "issuedOn": "2024-12-24",
    "customerId": "02941a68-74a9-44a1-adb0-4802222646bf",
    "items": [
        {
            "name": "calculating",
            "price": 407.41,
            "amount": 756
        },
        {
            "name": "Tactics",
            "price": 830.98,
            "amount": 822
        },
        {
            "name": "transmitting",
            "price": 207.63,
            "amount": 937
        }
    ]
}
```
## GET `api/invoices/:id`
#### Path Variables
- id
## PUT `api/invoices/:id`
#### Path Variables
- id
#### Body
```json
{
    "id": "abc",
    "invoiceNumber": "052024",
    "issuedOn": "2024-12-24",
    "expectedOn": "2025-01-13",
    "paidOn": "2024-12-30",
    "status": "PAID",
    "totalPriceInCents": 119309151,
    "customerId": "02941a68-74a9-44a1-adb0-4802222646bf"
}
```

## GET `api/payment`
#### Query Params
- sort
- size
- page
## POST `api/payment`
```json
{
    "date": "2024-12-30",
    "amount": 119309151,
    "invoiceId": "052024"
}
```
## GET `api/payments/invoice/:id`
#### Path Variables
- id
#### Query Params
- sort
- size
- page