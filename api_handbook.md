# This document provides guidelines for using the Weather API in the DailySkies project.

## Table of Contents

1. [Overview](#Overview)
2. [Authentication](#authentication)
3. [Request and Response Format](#request-and-response-format)
4. [Error Handling and Rate Limiting](#error-handling-and-rate-limting)

## Overview

**DailySkies** uses the WeatherAPI to retrieve current and forecasted weather data based on a user-provided location.
    
- Protocol : 'HTTP'
- Format : 'JSON'

## Authentication

- To get started, you must visit weatherapi.com and create a free account. 
- This will generate an API key that will be later used in fetching the data from the API.
- Do not expose your API key in public repositories if it's tied to sensitive data or has billing enabled. It is recommended to store on your local machine.
- Replace YOUR_API_KEY with the key assigned to your account.


The API key is then used as a query parameter for an asynchronous HTTP request.
URL: https://api.weatherapi.com/v1/forecast.json?key=YOUR_API_KEY&q=LOCATION&days=7

- The API key will expire within 14 days unless upgraded to the paid version. 

## Request and Response Format

The JSON response will follow this structure:
```json
{
    "location": {               <!-- Info about the requested city: name, country, lat, lon, localtime, etc.-->
    "name": "xyz",
    "region": "xyz",
    "country": "xyz",
    "lat": 0.0,
    "lon": 0.0,
    "tz_id": "xyz",
    "localtime_epoch": 0,
    "localtime": "xyz"
},
"current": {                    <!-- Current weather conditions: Temperature, Weather description and icon, etc.  -->
    "last_updated_epoch": 0,
    "last_updated": "xyz",  
    "temp_c": 0.0,
    "temp_f": 0.0,
    "is_day": 1,
    "condition": {
    "text": "xyz",
    "icon": "xyz",
    "code": 0
},
    "wind_mph": 0.0,
    "wind_kph": 0.0,
    "wind_degree": 0,
    "wind_dir": "xyz",
    "pressure_mb": 0.0,
    "pressure_in": 0.0,
    "precip_mm": 0.0,
    "precip_in": 0.0,
    "humidity": 0,
    "cloud": 0,
    "feelslike_c": 0.0,
    "feelslike_f": 0.0,
    "vis_km": 0.0,
    "vis_miles": 0.0,
    "uv": 0.0,
    "gust_mph": 0.0,
    "gust_kph": 0.0
}
}
```
## Error Handling and Rate Limiting

- Standard error responses follow this format:
{
    "error": {
        "code": 2006,
        "message": "API key provided is invalid"
}
}

  - The free plan allows up to 5 million API calls per month. 
  - If rate limits are exceeded, the server will return HTTP 429 or an error object with an appropriate message.





