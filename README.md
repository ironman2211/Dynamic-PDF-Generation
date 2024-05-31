# Dynamic PDF Generator

This Spring Boot application provides RESTful endpoints for generating, downloading, and generating-and-downloading PDF
invoices dynamically.

### Endpoints

##  1.Generate PDF

POST /api/generate-pdf

##### Description

Generates a PDF invoice based on the provided invoice request.

#### Request Body

Content-Type: application/json
```json
{
  "seller": "XYZ Pvt. Ltd.",
  "sellerGstin": "29AABBCCDD121ZD",
  "sellerAddress": "New Delhi, India",
  "buyer": "Vedant Computers",
  "buyerGstin": "29AABBCCDD131ZD",
  "buyerAddress": "New Delhi, India",
  "items": [
    {
      "name": "Product 1",
      "quantity": "12 Nos",
      "rate": 123.00,
      "amount": 1476.00
    }
  ]
}
```
### Response
##### Status Code: 200 OK
##### Content-Type: text/plain
##### Response Body: Path to the generated PDF file



## 2.Download PDF
Endpoint

GET /api/download-pdf?pdfPath={pdfPath}

##### Description

Downloads the PDF invoice with the specified path.

#### Request Parameter
##### pdfPath: Path to the PDF file to download

### Response

##### Status Code: 200 OK
##### Content-Type: application/pdf
##### Response Body: PDF file
## 3.Generate and Download PDF
### Endpoints

POST /api/generate-and-download-pdf
##### Description
Generates and immediately downloads a PDF invoice based on the provided invoice request.

#### Request Body
#### Content-Type: application/json
```json
{
  "seller": "XYZ Pvt. Ltd.",
  "sellerGstin": "29AABBCCDD121ZD",
  "sellerAddress": "New Delhi, India",
  "buyer": "Vedant Computers",
  "buyerGstin": "29AABBCCDD131ZD",
  "buyerAddress": "New Delhi, India",
  "items": [
    {
      "name": "Product 1",
      "quantity": "12 Nos",
      "rate": 123.00,
      "amount": 1476.00
    }
  ]
}
```


#### Response
#### Status Code: 200 OK
#### Content-Type: application/pdf
#### Response Body: PDF file