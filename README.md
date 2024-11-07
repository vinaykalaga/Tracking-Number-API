# Tracking Number API

This project is a RESTful API built with Spring Boot, designed to generate and validate tracking numbers for shipments. It includes custom validations for input data and is ready for deployment on Google Cloud Platform (GCP).

## Features

- **Tracking Number Generation**: Generates a unique tracking number based on input parameters.
- **Input Validation**: Custom validation logic for various parameters, including country codes, weight, date format, and customer details.
- **Google Cloud Platform Ready**: Configured to be deployed on GCP with a sample `app.yaml` file for Google App Engine.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Lombok** for reducing boilerplate code
- **Google Cloud Platform (GCP)** for deployment


### Running Locally

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/tracking-number-api.git
   cd tracking-number-api

2. **Build and Run the Application**:
   ```bash
   mvn spring-boot:run
   
3. **Deploy using Google Cloud SDK**:
   ```bash
   gcloud app deploy

4. **API Endpoint**:
   Endpoint: GET /api/next-tracking-number 
   
   Response:
   
    Success:
		{
			"success": true,
			"tracking_number": "M42G59659030TJ2A",
			"created_at": "2024-11-08T19:29:32+08:00"
		}
	Error:
		{
			"success": false,
			"error": "Weight must be a positive number with up to three decimal places."
		}

